package eu.accesa.prointerhyp.model.dto;

public class SortingAndFilteringDto {

    private int itemsPerPage;
    private String sortByField;
    private String orderByField;
    private String orderDirection;
    private String filterKeyword;

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public String getSortByField() {
        return sortByField;
    }

    public void setSortByField(String sortByField) {
        this.sortByField = sortByField;
    }

    public String getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(String orderByField) {
        this.orderByField = orderByField;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getFilterKeyword() {
        return filterKeyword;
    }

    public void setFilterKeyword(String filterKeyword) {
        this.filterKeyword = filterKeyword;
    }

    @Override
    public String toString() {
        return "SortingAndFilteringDto{" +
                "itemsPerPage=" + itemsPerPage +
                ", sortByField='" + sortByField + '\'' +
                ", orderByField='" + orderByField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", filterKeyword='" + filterKeyword + '\'' +
                '}';
    }
}