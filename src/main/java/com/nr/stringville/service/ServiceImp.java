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
        User user = userRepo.findById(name).orElseGet((Supplier<? extends User>) new User(name,0,0,0));
        if (checkSubmission(str)) {
            user.setScore(getScore(str) + user.getScore());
            user.setValidSubmissions(user.getValidSubmissions() + 1);
        }
        user.setTotalSubmissions(user.getTotalSubmissions() + 1);
        return userRepo.saveAndFlush(user);
    }

    @Override
    public void reset() {
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
            if (i != userList.size() - 1 || i != 9) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String getHealth() {
        List<User> userList= userRepo.findAll();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            sb.append(user.getName() + ": Total Submissions--" + user.getTotalSubmissions()
                    +", Total Valid Submissions--" + user.getValidSubmissions()
            + ", Total Invalid Submissions--" + (user.getTotalSubmissions() - user.getValidSubmissions()));
            if (i != userList.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean checkSubmission(String str) {
        if (str == null || str.length() == 0 || str.length() > 10000) {
            return false;
        }
        return true;
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
