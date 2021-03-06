/*
 * Copyright 2012-2014 MarkLogic Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marklogic.client.impl;

import java.util.Iterator;
import com.marklogic.client.Page;

public class BasicPage<T> implements Page<T> {
    private Iterable<T> iterable;
    private long start;
    private Long size = null;
    private long pageSize;
    private long totalSize;

    protected BasicPage(Class<T> type) {
    }

    public BasicPage(Iterable<T> iterable, long start, long pageSize, long totalSize) {
        this.iterable = iterable;
        this.start = start;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
    }

    public Iterator<T> iterator() {
        return iterable.iterator();
    }

    public long getStart() {
        return start;
    }

    public BasicPage<T> setStart(long start) {
        this.start = start;
		return this;
    }

    public long getPageSize() {
        return pageSize;
    }

    public BasicPage<T> setPageSize(long pageSize) {
        this.pageSize = pageSize;
		return this;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public BasicPage<T> setTotalSize(long totalSize) {
        this.totalSize = totalSize;
		return this;
    }

    public BasicPage<T> setSize(long size) {
        this.size = new Long(size);
		return this;
    }

    public long size() {
        if ( size != null ) return size.longValue();
        if ( hasNextPage() ) {
            return getPageSize();
        } else if ((getTotalSize() % getPageSize()) == 0) {
            return getPageSize();
        } else {
            return getTotalSize() % getPageSize();
        }
    }

    public long getTotalPages() {
        return (long) Math.ceil((double) getTotalSize() / (double) getPageSize());
    }

    public boolean hasContent() {
        return size() > 0;
    }

    public boolean hasNextPage() {
        return getPageNumber() < getTotalPages();
    }

    public boolean hasPreviousPage() {
        return getPageNumber() > 0;
    }

    public long getPageNumber() {
        return (long) Math.floor((double) start / (double) getPageSize()) + 1;
    }

    public boolean isFirstPage() {
        return getPageNumber() == 1;
    }

    public boolean isLastPage() {
        return getPageNumber() == getTotalPages();
    }
}
