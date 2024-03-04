package ru.clevertec.cachestarter.cache.impl;

import lombok.Data;
import ru.clevertec.cachestarter.cache.Cache;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LRUCache implements Cache {

    private final int size;
    private final ConcurrentHashMap<Long, CacheElement> cache;

    public LRUCache(int size) {
        this.size = size;
        this.cache = new ConcurrentHashMap<>(size);
    }

    @Override
    public boolean put(Long key, Object value) {
        CacheElement element = new CacheElement(LocalDateTime.now(), value);
        if (cache.size() == this.size) {
            evictElement();
        }
        cache.put(key, element);
        return true;
    }

    @Override
    public Object get(Long key) {
        if (cache.containsKey(key)) {
            CacheElement element = cache.get(key);
            element.setDateTimeOfLastRequest(LocalDateTime.now());
            return element.getValue();
        } else {
            return null;
        }
    }

    @Override
    public List<Object> getAll() {
        Collection<CacheElement> cacheElements = cache.values();
        cacheElements = cacheElements.stream()
                .peek(c -> c.setDateTimeOfLastRequest(LocalDateTime.now()))
                .toList();
        return cacheElements.stream()
                .map(CacheElement::getValue)
                .toList();
    }

    @Override
    public boolean delete(Long key) {
        if (cache.containsKey(key)) {
            CacheElement removed = cache.remove(key);
            return removed != null;
        } else return false;
    }

    private void evictElement() {
        CacheElement elementToRemove = cache.values().stream()
                .min(Comparator.comparing(CacheElement::getDateTimeOfLastRequest))
                .orElse(null);
        if (elementToRemove != null) {
            Long keyToRemove = this.getKeyByValue(cache, elementToRemove);
            cache.remove(keyToRemove);
        }
    }

    private Long getKeyByValue(Map<Long, CacheElement> cache, CacheElement value) {
        for (Map.Entry<Long, CacheElement> entry : cache.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Data
    private static class CacheElement {

        LocalDateTime dateTimeOfLastRequest;
        Object value;

        public CacheElement(LocalDateTime dateTimeOfLastRequest, Object value) {
            this.dateTimeOfLastRequest = dateTimeOfLastRequest;
            this.value = value;
        }
    }

}
