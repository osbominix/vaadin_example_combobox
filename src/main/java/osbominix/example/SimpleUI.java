/**
 * @author yl
 *
 * create a simple vaadin project in maven
 *
 * by click on button, the times will be counted and shown in the label above.
 */
package osbominix.example;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import javax.servlet.annotation.WebServlet;

@Title("Combobox Example")
@Theme("valo")
public class SimpleUI extends UI {
    private static final long serialVersionUID = 511085335415683713L;
    private int count = 0;
    private ComboBox food;
    private ComboBox list;
    private TextField price;

    private String vegetables = "Vegetables";
    private String fruit = "Fruit";

    private String carrots = "Carrots";
    private String potato = "Potato";

    private String avocado = "Avocado";
    private String melon = "Melon";

    private String priceCarrots = "1.1 Euro / Kilo";
    private String pricePatato = "1.2 Euro / Kilo";
    private String priceAvocado = "1.3 Euro / Kilo";
    private String priceMelon = "1.4 Euro / Kilo";
    private Property.ValueChangeListener valueChangeListener;

    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout content = new VerticalLayout();
        content.setWidth("350px");
        content.setHeight("350px");
        content.setMargin(true);
        setContent(content);

        food = new ComboBox("Food");
        food.setNullSelectionAllowed(false);
        food.addItem(vegetables);
        food.addItem(fruit);
        food.select(vegetables);

        //Vegetable: "Carrots", "Potato"
        //Fruit: "Avocado", "Melon"
        list = new ComboBox("List");
        list.setNullSelectionAllowed(false);
        list.addItem(carrots);
        list.addItem(potato);


        list.select(carrots);

        price = new TextField("Price");
        price.setValue(priceCarrots);
        price.setReadOnly(true);

        addCustomerListener();

        content.addComponent(food);
        content.addComponent(list);
        content.addComponent(price);

        valueChangeListener = new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                    price.setReadOnly(false);

                    if (list.getValue().equals(carrots)) {
                        price.setValue(priceCarrots);
                    } else if (list.getValue().equals(potato)) {
                        price.setValue(pricePatato);
                    }

                    if (list.getValue().equals(avocado)) {
                        price.setValue(priceAvocado);
                    } else if (list.getValue().equals(melon)) {
                        price.setValue(priceMelon);
                    }

                    price.setReadOnly(true);
            }
        };

        list.addValueChangeListener(valueChangeListener);

    }

    private void addCustomerListener() {
        food.addValueChangeListener(event-> {

            list.removeValueChangeListener(valueChangeListener);

            list.setNullSelectionAllowed(true);

            if (food.getValue().equals(vegetables)) {
                list.removeAllItems();

                list.addItem(carrots);
                list.addItem(potato);

                list.addValueChangeListener(valueChangeListener);

                list.select(carrots);

            } else if (food.getValue().equals(fruit)) {

                list.removeAllItems();

                list.addItem(avocado);
                list.addItem(melon);

                list.addValueChangeListener(valueChangeListener);

                list.select(avocado);
            }
            list.setNullSelectionAllowed(false);
        });


    }

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(
            productionMode = false,
            ui = SimpleUI.class)
    public static class Servlet extends VaadinServlet {
    }

}
