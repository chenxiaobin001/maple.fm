package com.example.maplefreemarket;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class InfiniteScrollListener implements OnScrollListener, Parcelable{
	
	static class SetLoading{
		boolean loading;
	}
	private int bufferItemCount  = 2;
	// The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 1;
    
    private SetLoading setLoading = new SetLoading();
    
    public InfiniteScrollListener() {
    	bufferItemCount  = 3;
    }
    
    public void initInfiniteScrollListener() { 
    	bufferItemCount  = 3;
    	currentPage = 0;
        previousTotalItemCount = 0;
        loading = true;
    }
    
    public InfiniteScrollListener(SetLoading setLoading) {
        this.setLoading = setLoading;
    }
    
    public int getBufferItemCount() {
		return bufferItemCount;
	}

	public void setBufferItemCount(int bufferItemCount) {
		this.bufferItemCount = bufferItemCount;
	}
	
    public void loadMore(int page, int totalItemsCount) {
    }
    
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Do Nothing
    }
    
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
    	if (setLoading.loading == false){			//reset listener from outer.
    		setLoading.loading = true;
 //   		System.out.println("same");
    		loading = false;
    	}
        if (totalItemCount < previousTotalItemCount) {    
        	loading = false;
        	this.previousTotalItemCount = totalItemCount;
 //       	System.out.println("firstVisibleItem" + firstVisibleItem + " visibleItemCount" + visibleItemCount + " totalItemCount" + totalItemCount + " jajaja");
 //       	System.out.println("loading" + loading);
        	this.currentPage = startingPageIndex;
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
    
    
    protected InfiniteScrollListener(Parcel in) {
        bufferItemCount = in.readInt();
        currentPage = in.readInt();
        previousTotalItemCount = in.readInt();
        loading = in.readByte() != 0x00;
        startingPageIndex = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bufferItemCount);
        dest.writeInt(currentPage);
        dest.writeInt(previousTotalItemCount);
        dest.writeByte((byte) (loading ? 0x01 : 0x00));
        dest.writeInt(startingPageIndex);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<InfiniteScrollListener> CREATOR = new Parcelable.Creator<InfiniteScrollListener>() {
        @Override
        public InfiniteScrollListener createFromParcel(Parcel in) {
            return new InfiniteScrollListener(in);
        }

        @Override
        public InfiniteScrollListener[] newArray(int size) {
            return new InfiniteScrollListener[size];
        }
    };
}
