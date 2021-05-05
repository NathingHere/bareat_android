package com.example.bareat_android.setup.client

import com.example.data.*
import com.example.domain.client.MockClient

class MockApiClient : MockClient {

    override suspend fun getRestaurantList(): Either<String, List<Restaurant>> = value(mockRestaurantList)

    override suspend fun getDishList(id: Int): Either<String, List<Dish>> = value(mockDishList)

    override suspend fun getCommentList(id: Int): Either<String, List<ReviewRestaurant>> = value(mockReviewList)



    private val mockRestaurantList = listOf(
        Restaurant(1, "dfsfggdsfdcg","qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 4.15F, true, "qwerty@gmail.com", 123456789),
        Restaurant(1,"basdkasl;djlkjer", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 4.76F, false, "qwerty@gmail.com", 123456789),
        Restaurant(2,"clkasjdjklher l", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 2.34F, true, "qwerty@gmail.com", 123456789),
        Restaurant(3,"qweerwwedf", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 2.34F, true, "qwerty@gmail.com", 123456789),
        Restaurant(4,"qweerwwedf", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 2.34F, true, "qwerty@gmail.com", 123456789),
        Restaurant(5,"isdhjakeghw adkj asd", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 2.34F, true, "qwerty@gmail.com", 123456789),
        Restaurant(6,"qweerwwedf", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 3.34F, true, "qwerty@gmail.com", 123456789),
        Restaurant(7,"abasdasdasdasr", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 1.34F, true, "qwerty@gmail.com", 123456789),
        Restaurant(8,"qweerwwedf", "qweqweqw", "https://media-cdn.tripadvisor.com/media/photo-s/1a/b8/46/6d/london-stock.jpg", "qweqqw qweqwe", 2.323, 2.23, "restaurante", 120, 3.34F, true, "qwerty@gmail.com", 123456789)
        )

    private val mockDishList = listOf(
            Dish(1, "asdasdasd asd asd aasdasd", "Tapa", "Descripcion del plato asdasdadqweda asd asdad asdawdawdwa", 4.99, "https://restaurantden.com/wp-content/uploads/2017/09/pexels.jpg", 3.5F),
            Dish(2, "asdasdasd asd asd aasdasd", "Tapa", "Descripcion del plato asdasdadqweda asd asdad asdawdawdwa", 4.99, "https://restaurantden.com/wp-content/uploads/2017/09/pexels.jpg", 3.5F),
            Dish(3, "asdasdasd asd asd aasdasd", "Tapa", "Descripcion del plato asdasdadqweda asd asdad asdawdawdwa", 4.99, "https://restaurantden.com/wp-content/uploads/2017/09/pexels.jpg", 3.5F),
            Dish(4, "asdasdasd asd asd aasdasd", "Tapa", "Descripcion del plato asdasdadqweda asd asdad asdawdawdwa", 4.99, "https://restaurantden.com/wp-content/uploads/2017/09/pexels.jpg", 3.5F),
            Dish(5, "asdasdasd asd asd aasdasd", "Tapa", "Descripcion del plato asdasdadqweda asd asdad asdawdawdwa", 4.99, "https://restaurantden.com/wp-content/uploads/2017/09/pexels.jpg", 3.5F),
            Dish(6, "asdasdasd asd asd aasdasd", "Tapa", "Descripcion del plato asdasdadqweda asd asdad asdawdawdwa", 4.99, "https://restaurantden.com/wp-content/uploads/2017/09/pexels.jpg", 3.5F),
            Dish(7, "asdasdasd asd asd aasdasd", "Tapa", "Descripcion del plato asdasdadqweda asd asdad asdawdawdwa", 4.99, "https://restaurantden.com/wp-content/uploads/2017/09/pexels.jpg", 3.5F),
            Dish(8, "asdasdasd asd asd aasdasd", "Tapa", "Descripcion del plato asdasdadqweda asd asdad asdawdawdwa", 4.99, "https://restaurantden.com/wp-content/uploads/2017/09/pexels.jpg", 3.5F),
            Dish(9, "asdasdasd asd asd aasdasd", "Tapa", "Descripcion del plato asdasdadqweda asd asdad asdawdawdwa", 4.99, "https://restaurantden.com/wp-content/uploads/2017/09/pexels.jpg", 3.5F),

            )

    private val mockReviewList = listOf(
            ReviewRestaurant(1, "Javier Lopez Jimenez", 4.43F, "asdjalikfj lajlkdfsh asdkljfh sdfsjfkg fsdgkjahlkjlsdfg kjdafhkj ldfh ksjdfjkh lsdfkhj sdfkjhl sdfjk hlsdfjkhl dsf"),
            ReviewRestaurant(2, "asd wadw ed aw dwa d awadwa daa da dwad",2.43F, "asdjalikfj lajlkdfsh asdkljfh sdfsjfkg fsdgkjahlkjlsdfg kjdafhkj ldfh ksjdfjkh lsdfkhj sdfkjhl sdfjk hlsdfjkhl dsf"),
            ReviewRestaurant(3, "asd wadw ed aw dwa d awadwa daa da dwad",1.43F, "asdjalikfj lajlkdfsh asdkljfh sdfsjfkg fsdgkjahlkjlsdfg kjdafhkj ldfh ksjdfjkh lsdfkhj sdfkjhl sdfjk hlsdfjkhl dsf"),
            ReviewRestaurant(4, "asd wadw ed aw dwa d awadwa daa da dwad",4F, "asdjalikfj lajlkdfsh asdkljfh sdfsjfkg fsdgkjahlkjlsdfg kjdafhkj ldfh ksjdfjkh lsdfkhj sdfkjhl sdfjk hlsdfjkhl dsf"),
            ReviewRestaurant(5, "asd wadw ed aw dwa d awadwa daa da dwad",0.43F, "asdjalikfj lajlkdfsh asdkljfh sdfsjfkg fsdgkjahlkjlsdfg kjdafhkj ldfh ksjdfjkh lsdfkhj sdfkjhl sdfjk hlsdfjkhl dsf"),
            ReviewRestaurant(6, "asd wadw ed aw dwa d awadwa daa da dwad",3.43F, "asdjalikfj lajlkdfsh asdkljfh sdfsjfkg fsdgkjahlkjlsdfg kjdafhkj ldfh ksjdfjkh lsdfkhj sdfkjhl sdfjk hlsdfjkhl dsf"),

    )

}