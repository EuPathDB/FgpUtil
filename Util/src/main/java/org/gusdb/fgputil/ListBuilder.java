package org.gusdb.fgputil;

import java.util.ArrayList;
import java.util.List;

/**
 * Convenience class for building Lists.
 * 
 * @author rdoherty
 *
 * @param <T> type contained in the enclosed List
 */
public class ListBuilder<T> {

  @SafeVarargs
  public static <T> List<T> asList(T... objs) {
    List<T> list = new ArrayList<>();
    for (T obj : objs) {
      list.add(obj);
    }
    return list;
  }

  private List<T> _list;

  public ListBuilder() {
    _list = new ArrayList<T>();
  }

  public ListBuilder(List<T> list) {
    _list = list;
  }

  public ListBuilder(T obj) {
    this();
    _list.add(obj);
  }

  public ListBuilder<T> add(T obj) {
    _list.add(obj);
    return this;
  }

  public ListBuilder<T> addIf(boolean add, T obj) {
    if (add) _list.add(obj);
    return this;
  }

  public ListBuilder<T> addAll(List<T> list) {
    _list.addAll(list);
    return this;
  }

  public List<T> toList() {
    return _list;
  }
}
