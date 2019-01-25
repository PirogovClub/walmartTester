package club.pirogov.leetcode;

import club.pirogov.wlmclc.pageobjects.ListNode;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Test2Class {
	
	
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
               
        ListNode  l3 = new ListNode(l1.val);
        int i = 1;
           while (l1.next != null){
               l1 = l1.next;
               l3.next = new ListNode(l1.val);
               System.out.println("l3.val ="+ l3.val + " i:"+i);
               i++;
           } 
        
        return l3;
    }
    
    public static void main(String parr[]) {
    	ListNode l1 = new ListNode(3);
    	ListNode l2 = new ListNode(15);
    	int i;
    	for (i=4;i<=6;i++) {
    		l1.next = new ListNode(i);
    		l2.next = new ListNode(i*5);
    		System.out.println("l1.val ="+ l1.val + " i:"+i);
    		System.out.println("l2.val ="+l2.val + " i:"+i);
    		l1 = l1.next;
    		l2 = l2.next;
    		
    	}
    	ListNode l3=addTwoNumbers(l1,l2);
    	 while (l3.next != null){
    		 System.out.println("l3.val ->"+ l3.val + " i:"+i);
    	 }
    }
}