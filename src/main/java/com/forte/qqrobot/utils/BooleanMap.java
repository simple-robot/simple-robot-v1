package com.forte.qqrobot.utils;

import java.util.*;

/**
 * key为boolean类型的紧凑型map，非线程安全
 *
 * @author <a href="https://github.com/ForteScarlet"> ForteScarlet </a>
 */
public class BooleanMap<V> extends AbstractMap<Boolean, V> implements java.io.Serializable, Cloneable {

    /**
     * 长度
     */
    private transient int size = 0;

    /**
     * 参数值，默认为长度为2的数组
     * true: 0
     * false: 1
     */
    private Object[] values = new Object[2];

    /**
     * key set
     */
    private transient Set<Boolean> keySet;

    private transient Collection<V> valueCollections;

    /**
     * This field is initialized to contain an instance of the entry set
     * view the first time this view is requested.  The view is stateless,
     * so there's no reason to create more than one.
     */
    private transient Set<Map.Entry<Boolean, V>> entrySet;

    /**
     * Distinguished non-null value for representing null values.
     *
     * @see EnumMap
     */
    private static final Object NULL = new Object() {
        public int hashCode() {
            return 0;
        }

        public String toString() {
            return "java.util.EnumMap.NULL";
        }
    };

    /* --- 构造 --- */

    public BooleanMap(){  }

    public BooleanMap(V trueValue, V falseValue){
        put(true, trueValue);
        put(false, falseValue);
    }


    public BooleanMap(Map<Boolean, V> m){
        for (Map.Entry<Boolean, V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }


    @Override
    public BooleanMap<V> clone() {
        BooleanMap<V> result;
        try {
            result = (BooleanMap<V>) super.clone();
        } catch(CloneNotSupportedException e) {
            throw new AssertionError();
        }
        result.values = result.values.clone();
        result.entrySet = null;
        return result;
    }

    protected int checkIndex(boolean key) {
        return key ? 0 : 1;
    }

    protected boolean checkKey(int index) {
        return index == 0;
    }

    /**
     * @see EnumMap
     */
    private Object maskNull(Object value) {
        return (value == null ? NULL : value);
    }

    /**
     * @see EnumMap
     */
    @SuppressWarnings("unchecked")
    private V unmaskNull(Object value) {
        return (V) (value == NULL ? null : value);
    }

    /**
     * 放置一个值
     *
     * @param key   Boolean 类型的key
     * @param value value
     * @return
     */
    protected V putValue(boolean key, V value) {
        int index = checkIndex(key);
        Object old = values[index];
        values[index] = maskNull(value);
        if (old == null) {
            size++;
        }
        return (V) unmaskNull(old);
    }

    /**
     * 移除一个值
     *
     * @param key boolean key
     * @return removed value
     */
    protected V removeValue(boolean key) {
        int index = checkIndex(key);
        Object old = values[index];
        values[index] = null;
        if (old != null) {
            size--;
        }
        return (V) unmaskNull(old);
    }

    /**
     * 获取一个值
     */
    protected V getValue(boolean key) {
        int index = checkIndex(key);
        return unmaskNull(values[index]);
    }


    /**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of key-value mappings in this map
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified
     * key.  More formally, returns <tt>true</tt> if and only if
     * this map contains a mapping for a key <tt>k</tt> such that
     * <tt>(key==null ? k==null : key.equals(k))</tt>.  (There can be
     * at most one such mapping.)
     *
     * @param key key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
     * key
     * @throws ClassCastException   if the key is of an inappropriate type for
     *                              this map
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *                              does not permit null keys
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean containsKey(Object key) {
        if (key instanceof Boolean) {
            return values[checkIndex((Boolean) key)] != null;
        } else {
            return false;
        }
    }

    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.  More formally, returns <tt>true</tt> if and only if
     * this map contains at least one mapping to a value <tt>v</tt> such that
     * <tt>(value==null ? v==null : value.equals(v))</tt>.  This operation
     * will probably require time linear in the map size for most
     * implementations of the <tt>Map</tt> interface.
     *
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     * specified value
     * @throws ClassCastException   if the value is of an inappropriate type for
     *                              this map
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified value is null and this
     *                              map does not permit null values
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean containsValue(Object value) {
        final Object newValue = maskNull(value);
        return newValue.equals(values[0]) || newValue.equals(values[1]);
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
     * key.equals(k))}, then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     *
     * <p>If this map permits null values, then a return value of
     * {@code null} does not <i>necessarily</i> indicate that the map
     * contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to {@code null}.  The {@link #containsKey
     * containsKey} operation may be used to distinguish these two cases.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     * {@code null} if this map contains no mapping for the key
     * @throws ClassCastException   if the key is of an inappropriate type for
     *                              this map
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *                              does not permit null keys
     *                              (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public V get(Object key) {
        return (key instanceof Boolean) ? getValue((boolean) key) : null;
    }

    /**
     * Associates the specified value with the specified key in this map
     * (optional operation).  If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.  (A map
     * <tt>m</tt> is said to contain a mapping for a key <tt>k</tt> if and only
     * if {@link #containsKey(Object) m.containsKey(k)} would return
     * <tt>true</tt>.)
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * (A <tt>null</tt> return can also indicate that the map
     * previously associated <tt>null</tt> with <tt>key</tt>,
     * if the implementation supports <tt>null</tt> values.)
     * @throws UnsupportedOperationException if the <tt>put</tt> operation
     *                                       is not supported by this map
     * @throws ClassCastException            if the class of the specified key or value
     *                                       prevents it from being stored in this map
     * @throws NullPointerException          if the specified key or value is null
     *                                       and this map does not permit null keys or values
     * @throws IllegalArgumentException      if some property of the specified key
     *                                       or value prevents it from being stored in this map
     */
    @Override
    public V put(Boolean key, V value) {
        return putValue(key, value);
    }

    public V putTrue(V value){
        return put(true, value);
    }

    public V putFalse(V value){
        return put(false, value);
    }

    /**
     * Removes the mapping for a key from this map if it is present
     * (optional operation).   More formally, if this map contains a mapping
     * from key <tt>k</tt> to value <tt>v</tt> such that
     * <code>(key==null ?  k==null : key.equals(k))</code>, that mapping
     * is removed.  (The map can contain at most one such mapping.)
     *
     * <p>Returns the value to which this map previously associated the key,
     * or <tt>null</tt> if the map contained no mapping for the key.
     *
     * <p>If this map permits null values, then a return value of
     * <tt>null</tt> does not <i>necessarily</i> indicate that the map
     * contained no mapping for the key; it's also possible that the map
     * explicitly mapped the key to <tt>null</tt>.
     *
     * <p>The map will not contain a mapping for the specified key once the
     * call returns.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this map
     * @throws ClassCastException            if the key is of an inappropriate type for
     *                                       this map
     *                                       (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if the specified key is null and this
     *                                       map does not permit null keys
     *                                       (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public V remove(Object key) {
        return (key instanceof Boolean) ? removeValue((boolean) key) : null;
    }

    public V removeTrue(){
        return remove(true);
    }

    public V removeFalse(){
        return remove(false);
    }

//    @Override
//    public void putAll(Map<? extends Boolean, ? extends V> m) {
//    }

    /**
     * Removes all of the mappings from this map (optional operation).
     * The map will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *                                       is not supported by this map
     */
    @Override
    public void clear() {
        values[0] = null;
        values[1] = null;
        size = 0;
    }

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     *
     * @return a set view of the keys contained in this map
     */
    @Override
    public Set<Boolean> keySet() {
        Set<Boolean> ks = keySet;
        if (ks == null) {
            ks = new BooleanKeySet();
            keySet = ks;
        }
        return ks;
    }

    private boolean containsMapping(Object key, Object value) {

        return isValidKey(key) &&
                maskNull(value).equals(values[checkIndex((Boolean)key)]);
    }


    private boolean removeMapping(Object key, Object value) {
        if (!isValidKey(key))
            return false;
        int index = checkIndex((Boolean) key);
        if (maskNull(value).equals(values[index])) {
            values[index] = null;
            size--;
            return true;
        }
        return false;
    }


    /**
     * Returns true if key is of the proper type to be a key in this
     * enum map.
     */
    private boolean isValidKey(Object key) {
        if (key == null)
            return false;

        // Cheaper than instanceof Enum followed by getDeclaringClass
        Class<?> keyClass = key.getClass();
        return keyClass == Boolean.class || keyClass == boolean.class;
    }


    @Override
    public String toString() {
        StringJoiner sjMap = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < values.length; i++) {
            final Object value = values[i];
            if(value != null){
                final Object unMaskValue = unmaskNull(value);
                sjMap.add(checkKey(i) + "=" + unMaskValue);
            }
        }
        return sjMap.toString();
    }

    private class BooleanKeySet extends AbstractSet<Boolean> {
        public Iterator<Boolean> iterator() {
            return new BooleanMapKeyIterator();
        }

        public int size() {
            return size;
        }

        public boolean contains(Object o) {
            return containsKey(o);
        }

        public boolean remove(Object o) {
            int oldSize = size;
            BooleanMap.this.remove(o);
            return size != oldSize;
        }

        public void clear() {
            BooleanMap.this.clear();
        }
    }


    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own <tt>remove</tt> operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt> and <tt>clear</tt> operations.  It does not
     * support the <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @return a collection view of the values contained in this map
     */
    @Override
    public Collection<V> values() {
        Collection<V> vs = valueCollections;
        if (vs == null) {
            vs = new Values();
            valueCollections = vs;
        }
        return vs;
    }


    private class Values extends AbstractCollection<V> {
        public Iterator<V> iterator() {
            return new BooleanMapValueIterator();
        }
        public int size() {
            return size;
        }
        public boolean contains(Object o) {
            return containsValue(o);
        }
        public boolean remove(Object o) {
            o = maskNull(o);

            for (int i = 0; i < values.length; i++) {
                if (o.equals(values[i])) {
                    values[i] = null;
                    size--;
                    return true;
                }
            }
            return false;
        }
        public void clear() {
            BooleanMap.this.clear();
        }
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation, or through the
     * <tt>setValue</tt> operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
     * <tt>clear</tt> operations.  It does not support the
     * <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @return a set view of the mappings contained in this map
     */
    @Override
    public Set<Map.Entry<Boolean, V>> entrySet() {
        Set<Map.Entry<Boolean, V>> es = entrySet;
        if (es != null)
            return es;
        else
            return entrySet = new EntrySet();
    }


    private class EntrySet extends AbstractSet<Map.Entry<Boolean, V>> {
        public Iterator<Map.Entry<Boolean, V>> iterator() {
            return new EntryIterator();
        }

        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> entry = (Map.Entry<?,?>)o;
            return containsMapping(entry.getKey(), entry.getValue());
        }
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> entry = (Map.Entry<?,?>)o;
            return removeMapping(entry.getKey(), entry.getValue());
        }
        public int size() {
            return size;
        }
        public void clear() {
            BooleanMap.this.clear();
        }
        public Object[] toArray() {
            return fillEntryArray(new Object[size]);
        }
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            int size = size();
            if (a.length < size)
                a = (T[])java.lang.reflect.Array
                        .newInstance(a.getClass().getComponentType(), size);
            if (a.length > size)
                a[size] = null;
            return (T[]) fillEntryArray(a);
        }
        private Object[] fillEntryArray(Object[] a) {
            int j = 0;
            if(values[0] != null){
                a[0] = unmaskNull(values[0]);
            }
            if(values[1] != null){
                a[1] = unmaskNull(values[1]);
            }
            return a;
        }
    }


    private int entryHashCode(boolean key) {
        return (Boolean.hashCode(key) ^ values[checkIndex(key)].hashCode());
    }



    private abstract class BooleanMapIterator<V> implements Iterator<V> {
        boolean index = true;
        boolean end = false;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            if(end){
                return false;
            }
            if (index) {
                final boolean containsTrue = containsKey(true);
                if (containsTrue) {
                    return true;
                } else {
                    index = false;
                    final boolean containsFalse = containsKey(false);
                    if (containsFalse) {
                        return true;
                    } else {
                        end = true;
                        return false;
                    }
                }
            } else {
                // index == false
                if (containsKey(false)) {
                    return true;
                } else {
                    end = true;
                    return false;
                }
            }
        }

        void checkEnd() {
            if (end)
                throw new IllegalStateException();
        }

    }

    /**
     * BooleanMap Iterator
     */
    private class BooleanMapKeyIterator extends BooleanMapIterator<Boolean> {
        /**
         * Returns the next element in the iteration.
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Boolean next() {
            checkEnd();
            Boolean next = index;
            index = !index;
            if (index) {
                end = true;
            }
            return next;
        }

    }

    /**
     * BooleanMap value Iterator
     */
    private class BooleanMapValueIterator extends BooleanMapIterator<V> {

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public V next() {
            checkEnd();
            V next = get(index);
            index = !index;
            if (index) {
                end = true;
            }
            return next;
        }
    }

    private class Entry implements Map.Entry<Boolean, V> {
        private int index;

        private Entry(boolean index) {
            this.index = checkIndex(index);
        }

        public Boolean getKey() {
            checkIndexForEntryUse();
            return checkKey(index);
        }

        public V getValue() {
            checkIndexForEntryUse();
            return unmaskNull(values[index]);
        }

        public V setValue(V value) {
            checkIndexForEntryUse();
            V oldValue = unmaskNull(values[index]);
            values[index] = maskNull(value);
            return oldValue;
        }

        public boolean equals(Object o) {
            if (index < 0)
                return o == this;

            if (!(o instanceof Map.Entry))
                return false;

            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            V ourValue = unmaskNull(values[index]);
            Object hisValue = e.getValue();
            final Object key = e.getKey();
            return (key instanceof Boolean) && (key == (Boolean) checkKey(index)) &&
                   (Objects.equals(ourValue, hisValue));
        }


        public int hashCode() {
            if (index < 0)
                return super.hashCode();

            return entryHashCode(checkKey(index));
        }

        public String toString() {
            if (index < 0)
                return super.toString();

            return (index == 0) + "="
                    + unmaskNull(values[index]);
        }

        private void checkIndexForEntryUse() {
            if (index < 0)
                throw new IllegalStateException("Entry was removed");
        }
    }


    private class EntryIterator extends BooleanMapIterator<Map.Entry<Boolean,V>> {
        private Entry lastReturnedEntry;

        public Map.Entry<Boolean,V> next() {
            checkEnd();
            lastReturnedEntry = new EntryIterator.Entry(checkIndex(index));
            index = !index;
            if(index){
                end = true;
            }
            return lastReturnedEntry;
        }

        public void remove() {
//            int lastReturnedIndex = checkIndex(index);
            int lastReturnedIndex =
                    ((null == lastReturnedEntry) ? -1 : lastReturnedEntry.index);
            super.remove();
            lastReturnedEntry.index = lastReturnedIndex;
            lastReturnedEntry = null;
        }

        private class Entry implements Map.Entry<Boolean,V> {
            private int index;

            private Entry(int index) {
                this.index = index;
            }

            public Boolean getKey() {
                checkIndexForEntryUse();
                return checkKey(index);
            }

            public V getValue() {
                checkIndexForEntryUse();
                return unmaskNull(values[index]);
            }

            public V setValue(V value) {
                checkIndexForEntryUse();
                V oldValue = unmaskNull(values[index]);
                values[index] = maskNull(value);
                return oldValue;
            }

            public boolean equals(Object o) {
                if (index < 0)
                    return o == this;

                if (!(o instanceof Map.Entry))
                    return false;

                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                V ourValue = unmaskNull(values[index]);
                Object hisValue = e.getValue();
                return (e.getKey() == (Boolean) checkKey(index) &&
                        (Objects.equals(ourValue, hisValue)));
            }

            public int hashCode() {
                if (index < 0)
                    return super.hashCode();

                return entryHashCode(checkKey(index));
            }

            public String toString() {
                if (index < 0)
                    return super.toString();

                return checkKey(index) + "="
                        + unmaskNull(values[index]);
            }

            private void checkIndexForEntryUse() {
                if (index < 0)
                    throw new IllegalStateException("Entry was removed");
            }
        }
    }


}
