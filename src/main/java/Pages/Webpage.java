package Pages;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.stream.Collectors;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;

public class Webpage {

    private final Page page;
    private final String allRowsLocator = ".rt-tr-group";
    private final String searchBoxLocator = "#searchBox";
    public Webpage (Page page) {
        this.page=page;
    }

    public void search(String query) {
        //заполнение текстового поля
        page.fill(searchBoxLocator, query);
        String expectedSelector = String.format("//div[@class='rt-td' and text()='%s']", query);
        //намеренное ожидание элемента на странице с использованием state.
        page.waitForSelector(expectedSelector, new Page.WaitForSelectorOptions().setState(VISIBLE));
    }




    public List<String> getVisibleNames() {
        return page.querySelectorAll(allRowsLocator) //находит коллекцию элементов
                .stream()
                .map(x->x.innerText()) //достает текст из каждого элемента
               // .filter(x->x.contains(" ")) //условие если текст из строчки не начинается на пробел
                .collect(Collectors.toList()); //собирает результаты в список
    }

}
