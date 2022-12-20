package com.doutoutdou.advent2022.day8;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Tree {
    private int value;
    private boolean isExtremity;
    private Tree top;
    private Tree bottom;
    private Tree right;
    private Tree left;
}
