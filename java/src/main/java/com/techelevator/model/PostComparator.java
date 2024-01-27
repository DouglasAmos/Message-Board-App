package com.techelevator.model;

public class PostComparator implements java.util.Comparator<Post> {
    @Override
    public int compare(Post o1, Post o2) {
        return o2.getEngagement() - o1.getEngagement();
    }
}
