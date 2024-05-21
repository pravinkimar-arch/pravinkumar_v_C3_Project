import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    @Spy
    Restaurant restaurant;
    LocalTime openingTime = LocalTime.parse("09:00:00");
    LocalTime closingTime = LocalTime.parse("21:00:00");

    @BeforeEach
    void setUp() {
        restaurant = Mockito.spy(new Restaurant("sam co", "chennai", openingTime, closingTime));
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    @ExtendWith(MockitoExtension.class)
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        LocalTime mockCurrentTime = LocalTime.parse("11:00:00");
        Mockito.doReturn(mockCurrentTime).when(restaurant).getCurrentTime();
        boolean isOpen = restaurant.isRestaurantOpen();
        assertTrue(isOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        LocalTime mockCurrentTime = LocalTime.parse("22:00:00");
        Mockito.doReturn(mockCurrentTime).when(restaurant).getCurrentTime();
        boolean isOpen = restaurant.isRestaurantOpen();
        assertFalse(isOpen);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>Order<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void order_value_is_calculated_when_non_empty_itemlist_passed() {
        Item newItem1 = new Item("Dosa", 50);
        Item newItem2 = new Item("Idly", 50);
        Item newItem3 = new Item("vada", 50);

        ArrayList<Item> itemList = new ArrayList<Item>();
        itemList.add(newItem1);
        itemList.add(newItem2);
        itemList.add(newItem3);
        restaurant.getTotalOrderValue(itemList);
    }

    @Test
    public void order_value_zero_when_items_list_is_empty() {

        ArrayList<Item> itemList = new ArrayList<Item>();
        restaurant.getTotalOrderValue(itemList);
    }

    @Test
    public void order_value_zero_when_null_is_passes() {

        ArrayList<Item> itemList = new ArrayList<Item>();
        itemList = null;
        restaurant.getTotalOrderValue(itemList);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<Order>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}