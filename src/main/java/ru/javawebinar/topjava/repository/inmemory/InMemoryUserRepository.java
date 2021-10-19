package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final AtomicInteger counter = new AtomicInteger(0);
    private static final Map<Integer, User> repository = new ConcurrentHashMap<>();
    {
        List<User> users = Arrays.asList(
                new User("cPetka", "petka@ya.ru", "peta", 1500, true, new HashSet<Role>() {{
                    add(Role.USER);
                    add(Role.ADMIN);
                }}),
                new User("bVanka", "vanka@ya.ru", "peta", 2500, true, new HashSet<Role>() {{
                    add(Role.USER);
                }}),
                new User("aStepa", "astep@ya.ru", "peta", 1000, true, new HashSet<Role>() {{
                    add(Role.ADMIN);
                }}),
                new User("aStepa", "bstep@ya.ru", "peta", 1000, true, new HashSet<Role>() {{
                    add(Role.ADMIN);
                }}),
                new User("aStepa", "cstep@ya.ru", "peta", 1000, true, new HashSet<Role>() {{
                    add(Role.ADMIN);
                }})
        );
        users.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        log.info("save {}", user);
        // handle case: update, but not present in storage
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values().stream()
                .sorted(Comparator.comparing(User::getName))
                .sorted(Comparator.comparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Optional<User> userOptional = repository.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
        return userOptional.orElse(null);
    }
}
