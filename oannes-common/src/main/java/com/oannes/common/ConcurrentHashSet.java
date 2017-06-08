package com.oannes.common;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1605414808940145054L;

	private static final Object PRESENT = new Object();

	private final ConcurrentHashMap<E, Object> map;
	public ConcurrentHashSet(){
	    map = new ConcurrentHashMap<E, Object>();
	}

    public ConcurrentHashSet(int initialCapacity){
        map = new ConcurrentHashMap<E, Object>(initialCapacity);
    }

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return map.keySet().iterator();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return map.keySet().size();
	}

	@Override
	public boolean add(E key) {
		// TODO Auto-generated method stub
	
		return map.putIfAbsent(key, PRESENT)!=null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		super.clear();
		map.clear();
	}

	@Override
	public boolean contains(Object key) {
		// TODO Auto-generated method stub
		return map.containsKey(key);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return map.remove(o)==PRESENT;
	}

}
