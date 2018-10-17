package cn.stormleo.domain;

import java.util.List;

public class PageModel02 {
    private int currentPageNum;
    private  int pageSize=5;
    private int totalRecords;
    private int totalPageNum;
    private int startIndex;
    private int prePageNum;
    private int nextPageNum;


    private List records;


    private int startPage;
    private int endPage;


    private String url;

    public PageModel02(int currentPageNum, int totalRecords,int pageSize ) {
        this.currentPageNum = currentPageNum;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;


        startIndex = (currentPageNum-1)*pageSize;

        totalPageNum = totalRecords%pageSize==0?(totalRecords/pageSize):(totalRecords/pageSize+1);

        startPage = currentPageNum - 4;

        endPage = currentPageNum + 4;

        if(totalPageNum>9){
            if(startPage < 1){
                startPage = 1;
                endPage = startPage+8;
            }
            if(endPage>totalPageNum){
                endPage = totalPageNum;
                startPage = endPage-8;
            }
        }else{
            startPage = 1;
            endPage = totalPageNum;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getPrePageNum() {
        prePageNum = currentPageNum-1;
        if(prePageNum<1){
            prePageNum = 1;
        }
        return prePageNum;
    }

    public int getNextPageNum() {
        nextPageNum = currentPageNum+1;
        if(nextPageNum>totalPageNum){
            nextPageNum = totalPageNum;
        }
        return nextPageNum;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }


    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }


    public int getPageSize() {
        return pageSize;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getTotalRecords() {
        return totalRecords;
    }


    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }


    public int getTotalPageNum() {
        return totalPageNum;
    }


    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }


    public int getStartIndex() {
        return startIndex;
    }


    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }





    public void setPrePageNum(int prePageNum) {
        this.prePageNum = prePageNum;
    }




    public void setNextPageNum(int nextPageNum) {
        this.nextPageNum = nextPageNum;
    }


    public List getRecords() {
        return records;
    }


    public void setRecords(List records) {
        this.records = records;
    }
}
