package com.orangesoft.handmadefood.abstraction;

public class Recipe {
    public int id;
    public String title;
    public String rss_title;
    public String content;
    public String cook_time;
    public String servings_number;
    public String slug;
    public String general_image;
    public String[] ingridients;
    public String[] ingridient_categories;
    public  String[] food_categories;
    //public  String user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRss_title() {
        return rss_title;
    }

    public void setRss_title(String rss_title) {
        this.rss_title = rss_title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCook_time() {
        return cook_time;
    }

    public void setCook_time(String cook_time) {
        this.cook_time = cook_time;
    }

    public String getServings_number() {
        return servings_number;
    }

    public void setServings_number(String servings_number) {
        this.servings_number = servings_number;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getGeneral_image() {
        return general_image;
    }

    public void setGeneral_image(String general_image) {
        this.general_image = general_image;
    }

    public String[] getIngridient_categories() {
        return ingridient_categories;
    }

    public void setIngridient_categories(String[] ingridient_categories) {
        this.ingridient_categories = ingridient_categories;
    }
}
