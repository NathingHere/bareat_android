package com.example.bareat_android.ui.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import coil.load
import com.example.bareat_android.R
import com.example.bareat_android.databinding.BookItemviewBinding
import com.example.bareat_android.databinding.FragmentProfileBinding
import com.example.bareat_android.databinding.ViewpagerImageBinding
import com.example.bareat_android.setup.extensions.initHorizontalRecycler
import com.example.bareat_android.setup.extensions.initVerticalRecycler
import com.example.bareat_android.setup.extensions.visible
import com.example.bareat_android.ui.base.BaseFragment
import com.example.bareat_android.ui.base.BaseRecyclerView
import com.example.bareat_android.ui.base.BaseViewHolder
import com.example.bareat_android.ui.base.BaseViewModel
import com.example.bareat_android.ui.customview.BareatToolbar
import com.example.bareat_android.ui.restaurant.RestaurantViewModel
import com.example.data.DataBook
import com.example.data.Dish
import com.example.data.Image
import com.example.data.ReviewRestaurant
import com.example.data.tosend.RateRestaurantBody
import kotlinx.android.synthetic.main.dialog_logout.*
import kotlinx.android.synthetic.main.dialog_rating.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val profileViewModel by viewModel<ProfileViewModel>()

    private val bookListAdapter = BookListAdapter() { onBookClick(it) }

    private lateinit var dialog: Dialog

    private var bookList = listOf<DataBook>()

    override fun initializeBinding(): FragmentProfileBinding {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding
    }

    override fun setToolbar() {
        provideToolbar().apply {
            initToolbar(BareatToolbar.ToolbarItemMenu.ProfileItem)
            visible()
            hideDoneCancelButtons()
            setBigToolbarTitle(getString(R.string.section_profile))
            setOnMenuItemClick(onSettingsClick = {showLogoutDialog()})
        }
    }

    override fun initView() {
        profileViewModel.init()



        with(binding) {

            rvBook.initVerticalRecycler(bookListAdapter)

            prefs.id?.let { profileViewModel.getBookList(it.toInt()) }
            profileViewModel.bookListData.observe(viewLifecycleOwner) { manageBookScreenState(it) }
            profileViewModel.deleteListData.observe(viewLifecycleOwner) { manageDeleteScreenState(it) }

        }
    }

    private fun manageDeleteScreenState(state: BaseViewModel.ScreenState<ProfileViewModel.DeleteState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageDeleteState(state.renderState)
            }
        }
    }

    private fun manageDeleteState(state: ProfileViewModel.DeleteState) {
        hideProgressDialog()
        when(state){
            is ProfileViewModel.DeleteState.SUCCESS -> {
                showToast("Se ha cancelado su reserva")
            }
            is ProfileViewModel.DeleteState.ERROR -> showToast(state.errorMessage)
        }
    }

    private fun manageBookScreenState(state: BaseViewModel.ScreenState<ProfileViewModel.BookState>?) {
        when(state) {
            BaseViewModel.ScreenState.LOADING -> showProgressDialog()
            is BaseViewModel.ScreenState.RenderData -> {
                manageBookState(state.renderState)
            }
        }
    }

    private fun manageBookState(state: ProfileViewModel.BookState) {
        hideProgressDialog()
        when(state){
            ProfileViewModel.BookState.Empty -> {
                binding.tvEmptyBook.visible()
            }
            is ProfileViewModel.BookState.SUCCESS -> {
                bookList = state.bookList
                bookListAdapter.updateList(state.bookList)
            }
            is ProfileViewModel.BookState.ERROR -> showToast(state.errorMessage)
        }
    }

    inner class BookListAdapter(private val onClick: (DataBook) -> Unit) : BaseRecyclerView<DataBook, BookListAdapter.Holder>() {

        override fun getViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
            BookItemviewBinding.inflate(layoutInflater, parent, false))

        inner class Holder(private val bookItemviewBinding: BookItemviewBinding) : BaseViewHolder<DataBook>(bookItemviewBinding) {

            override fun bindData(item: DataBook) {
                with(bookItemviewBinding) {
                    tvName.text = ""
                    tvDate.text = item.date
                    tvTime.text = item.hour?.length?.minus(3)?.let { item.hour!!.substring(0, it) }
                    tvNumberPerson.text = item.num.toString()
                    btnDelete.setOnClickListener { onClick.invoke(item) }
                }
            }

        }

    }

    private fun showLogoutDialog() {
        context?.let { it ->
            dialog = Dialog(it).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(true)
                setContentView(R.layout.dialog_logout)

                with(binding) {
                    closeYes.setOnClickListener {
                        prefs.clearLogin()
                        homeActivity().routeToLogin()
                    }
                    closeNo.setOnClickListener { dismiss() }
                }

                //Change window options:
                val lp = WindowManager.LayoutParams()
                lp.copyFrom(window?.attributes)
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.MATCH_PARENT
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
                window?.attributes = lp
            }
        }
    }

    private fun onBookClick(dataBook: DataBook) {
        dataBook.userId?.let { dataBook.id?.let { it1 -> profileViewModel.deleteBook(it, it1) } }
        bookList = bookList.filter { it.id != dataBook.id }
        bookListAdapter.updateList(bookList)
    }

}