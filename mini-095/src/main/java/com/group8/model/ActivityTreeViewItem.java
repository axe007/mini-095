package com.group8.model;

import org.bson.types.ObjectId;

public class ActivityTreeViewItem {
    private int tid;
    private int mid;
    private int bid;
    private ObjectId activityId;

    public ActivityTreeViewItem() { }

    public ActivityTreeViewItem(int tid, int mid, int bid, ObjectId activityId) {
        this.tid = tid;
        this.mid = mid;
        this.bid = bid;
        this.activityId = activityId;
    }

    public int getTid() { return tid; }
    public int getMid() { return mid; }
    public int getBid() { return bid; }
    public ObjectId getActivityId() { return activityId; }

    public void setTid(int tid) { this.tid = tid; }
    public void setMid(int mid) { this.mid = mid; }
    public void setBid(int bid) { this.bid = bid; }
    public void setActivityId(ObjectId activityId) { this.activityId = activityId; }

    @Override
    public String toString() {
        String activityTreeItem = + getTid() + " : " + getMid() + " : " + getBid() + " | activityId = " + getActivityId();
        return activityTreeItem;
    }
}
