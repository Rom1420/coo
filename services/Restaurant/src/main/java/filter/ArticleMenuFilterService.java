package filter;

import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Menu;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleMenuFilterService {

    public List<Article> filterArticlesByPrice(List<Article> articles, float maxPrice) {
        return articles.stream()
                .filter(article -> article.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public List<Article> filterArticlesByTime(List<Article> articles, int maxPreparationTime) {
        return articles.stream()
                .filter(article -> article.getTimeRequiredForPreparation() <= maxPreparationTime)
                .collect(Collectors.toList());
    }

    public List<Menu> filterMenusByPrice(List<Menu> menus, float maxPrice) {
        return menus.stream()
                .filter(menu -> menu.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public List<Menu> filterMenusByTime(List<Menu> menus, int maxPreparationTime) {
        return menus.stream()
                .filter(menu -> menu.getTotalTimeRequiredForPreparation() <= maxPreparationTime)
                .collect(Collectors.toList());
    }
}
