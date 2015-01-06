package com.example.maplefreemarket;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class InfiniteScrollListener implements OnScrollListener{

	private int bufferItemCount  = 3;
	// The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;
    
    public InfiniteScrollListener() {
    	bufferItemCount  = 3;
    }
    
    public InfiniteScrollListener(int bufferItemCount) {
        this.bufferItemCount = bufferItemCount;
    }
    
    public int getBufferItemCount() {
		return bufferItemCount;
	}

	public void setBufferItemCount(int bufferItemCount) {
		this.bufferItemCount = bufferItemCount;
	}
	
    public abstract void loadMore(int page, int totalItemsCount);
    
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Do Nothing
    }
    
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        if (totalItemCount < previousTotalItemCount) {
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true; 
            }
        }
 
        if (loading && (totalItemCount > previousTotalItemCount)) {
        	loading = false;
        	previousTotalItemCount = totalItemCount;
            currentPage++;
        }
 
        if (!loading && totalItemCount - visibleItemCount <= (firstVisibleItem + bufferItemCount)) {
            loadMore(currentPage, totalItemCount);
            loading = true;
        }
    }
}
