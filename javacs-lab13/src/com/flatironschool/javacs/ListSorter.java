/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        if (list.size() <= 1 || list == null) {
        	return list;
        }
        List<T> firstHalf = mergeSort(new LinkedList<T>(list.subList(0,list.size()/2)), comparator);
		List<T> secondHalf = mergeSort(new LinkedList<T>(list.subList(list.size()/2,list.size())), comparator);
		return mergeSortHelper(firstHalf, secondHalf, comparator);
	}

	public List<T> mergeSortHelper(List<T> leftList, List<T> rightList, Comparator<T> comparator) {
		int total = leftList.size()+rightList.size();
		List<T> newList = new LinkedList<T>();
		for (int i=0; i<total; i++) {
			if (leftList.size()==0){
				newList.add(i, rightList.get(0));
				rightList.remove(0);
			}
			else if (rightList.size()==0) {
				newList.add(i, leftList.get(0));
				leftList.remove(0);
			}
			else if (comparator.compare(leftList.get(0), rightList.get(0))<=0) {
				newList.add(i, leftList.get(0));
				leftList.remove(0);
			}
			else if (comparator.compare(leftList.get(0), rightList.get(0))>0) {
				newList.add(i, rightList.get(0));
				rightList.remove(0);
			}
			else {
				newList.add(i, leftList.get(0));
				leftList.remove(0);
			}
		}
		return newList;
	}
	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
		if (list!=null) {
	        PriorityQueue<T> queue = new PriorityQueue<T>(list.size(), comparator);
	        for (T element: list) {
	        	queue.offer(element);
	        }
	        list.clear();
	        while (!queue.isEmpty()) {
	        	list.add(queue.poll());
	        }
	    }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> queue = new PriorityQueue<T>(list.size(), comparator);
        for (T element: list) {
        	if (queue.size() < k) {
	        	queue.offer(element);
        	} else {
        		int comp = comparator.compare(element, queue.peek());
        		if (comp>0) {
        			queue.poll();
        			queue.offer(element);
        		}
        	}
	    }
	    List<T> newList = new LinkedList<T>();
	    while (!queue.isEmpty()) {
	        newList.add(queue.poll());
	    }
        return newList;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
