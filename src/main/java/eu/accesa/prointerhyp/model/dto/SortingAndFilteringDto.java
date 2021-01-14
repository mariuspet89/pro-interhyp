package eu.accesa.prointerhyp.model.dto;

public class SortingAndFilteringDto {

    private int itemsPerPage;
    private String orderDirection;
    private String filterKeyword;

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
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
                ", orderDirection='" + orderDirection + '\'' +
                ", filterKeyword='" + filterKeyword + '\'' +
                '}';
    }
}