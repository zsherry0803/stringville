package com.nr.stringville.service;

import com.nr.stringville.dto.User;
import com.nr.stringville.repo.UserRepo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@org.springframework.stereotype.Service
public class ServiceImp implements Service{

    private static int validSubmission;
    private static int invalidSubmission;
    private final UserRepo userRepo;

    public ServiceImp(UserRepo userRepo) {
        super();
        this.userRepo = userRepo;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User updateUser(String name, String str) {
        User user = userRepo.findById(name).orElse(new User(name,0));
        int score = getScore(str);
        user.setScore(user.getScore() + score);
        return userRepo.saveAndFlush(user);
    }

    @Override
    public void reset() {
        validSubmission = 0;
        invalidSubmission = 0;
        userRepo.deleteAll();
    }

    @Override
    public String getResult() {
        List<User> userList= userRepo.findAll();
        Collections.sort(userList, (a,b) -> b.getScore() - a.getScore());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < userList.size() && i < 10; i++) {
            User user = userList.get(i);
            sb.append(user.getName() + "," + user.getScore());
            if (i != userList.size() - 1 && i != 9) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String getHealth() {
        String health = "valid submissions: " + validSubmission + ", invalid submissions: " + invalidSubmission;
        return health;
    }

    @Override
    public int getScore(String str) {
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : str.toCharArray()) {
            map.computeIfAbsent(ch,k -> 0);
            map.put(ch,map.get(ch) + 1);
        }
        int score = 0;
        for (char ch : map.keySet()) {
            switch (ch) {
                case 'a': score += map.get(ch) == 3 ? 1 : 0;
                    break;
                case 'e': score += map.get(ch) == 2 ? 5 : 0;
                    break;
                case 'i': score += map.get(ch) / 2 * 2;
                    break;
                case 'g': score += map.get(ch) == 1 ? 3 : 0;
                    break;
                case 'u': score += map.get(ch);
                    break;
                case 'z': score += map.get(ch) * (-10);
                    break;
            }
        }
        return score;
    }
}
