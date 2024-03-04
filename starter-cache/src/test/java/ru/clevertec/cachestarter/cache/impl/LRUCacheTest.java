package ru.clevertec.cachestarter.cache.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.cachestarter.util.User;

import java.time.LocalDate;
import java.util.List;

class LRUCacheTest {

    private static LRUCache cache = new LRUCache(3);

    @BeforeEach
    void setUp() {
        cache = new LRUCache(3);
        User user1 = new User(1L, "Name1", "Surname1", 20,
                LocalDate.of(2023, 12, 12), true);
        User user2 = new User(2L, "Name2", "Surname2", 20,
                LocalDate.of(2023, 12, 12), true);
        cache.put(1L, user1);
        cache.put(2L, user2);
    }

    @Test
    void userGetShouldReturnEntityWithIdEqualTo1() {
        //given
        User expected = new User(1L, "Name1", "Surname1", 20,
                LocalDate.of(2023, 12, 12), true);

        //when
        Object actual = cache.get(1L);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void userGetAllShouldReturnEntitiesWithIdsEqualTo1And2() {
        //given
        User user1 = new User(1L, "Name1", "Surname1", 20,
                LocalDate.of(2023, 12, 12), true);
        User user2 = new User(2L, "Name2", "Surname2", 20,
                LocalDate.of(2023, 12, 12), true);
        List<User> expected = List.of(user1, user2);

        //when
        List<Object> actual = cache.getAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void userSetShouldAddThirdEntityToCache() {
        //given
        User user1 = new User(1L, "Name1", "Surname1", 20,
                LocalDate.of(2023, 12, 12), true);
        User user2 = new User(2L, "Name2", "Surname2", 20,
                LocalDate.of(2023, 12, 12), true);
        User user3 = new User(3L, "Name3", "Surname3", 20,
                LocalDate.of(2023, 12, 12), true);
        List<User> expected = List.of(user1, user2, user3);

        //when
        cache.put(3L, user3);
        List<Object> actual = cache.getAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void userSetShouldRemoveTheOldestEntityToGetCapacityForNewOneAndAddNewEntity() {
        //given
        User user1 = new User(1L, "Name1", "Surname1", 20,
                LocalDate.of(2023, 12, 12), true);
        User user2 = new User(2L, "Name2", "Surname2", 20,
                LocalDate.of(2023, 12, 12), true);
        User user3 = new User(3L, "Name3", "Surname3", 20,
                LocalDate.of(2023, 12, 12), true);
        User user4 = new User(4L, "Name4", "Surname4", 20,
                LocalDate.of(2023, 12, 12), true);
        List<User> expected = List.of(user2, user3, user4);

        cache.put(3L, user3);

        //when
        cache.put(4L, user4);
        List<Object> actual = cache.getAll();

        //then
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void userDeleteShouldDeleteEntityFromCache() {
        //given
        int sizeBeforeDelete = cache.getAll().size();

        //when
        cache.delete(1L);
        int sizeAfterDelete = cache.getAll().size();

        //then
        Assertions.assertNotEquals(sizeBeforeDelete, sizeAfterDelete);
    }
}