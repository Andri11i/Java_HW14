package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PetShelterApp {

    private static final String DATA_FILE = "pets.json";
    private List<Pet> pets;
    public Scanner scanner;
    public ObjectMapper objectMapper;

    public PetShelterApp() {
        this.objectMapper = new ObjectMapper();
        pets = loadPets();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        PetShelterApp app = new PetShelterApp();
        app.start();
    }


    public void start() {
        while (true) {
            showMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addPet();
                    break;
                case "2":
                    showAllPets();
                    break;
                case "3":
                    removePet();
                    break;
                case "4":
                    savePets();
                    System.out.println("Вихід з програми.");
                    return;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }


    private void showMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Додати тварину (add pet)");
        System.out.println("2. Переглянути тварин (show all)");
        System.out.println("3. Видалити тварину (take pet from shelter)");
        System.out.println("4. Вихід (exit)");
        System.out.print("Ваш вибір: ");
    }


    private void addPet() {
        System.out.print("Введіть ім'я тварини: ");
        String name = scanner.nextLine();
        System.out.print("Введіть вид тварини: ");
        String species = scanner.nextLine();
        System.out.print("Введіть вік тварини: ");
        int age = Integer.parseInt(scanner.nextLine());

        Pet newPet = new Pet(name, species, age);
        pets.add(newPet);
        System.out.println("Тварина додана в притулок.");
    }


    private void showAllPets() {
        if (pets.isEmpty()) {
            System.out.println("Притулок порожній.");
        } else {
            System.out.println("Список тварин у притулку:");
            for (int i = 0; i < pets.size(); i++) {
                System.out.println((i + 1) + ". " + pets.get(i));
            }
        }
    }


    private void removePet() {
        if (pets.isEmpty()) {
            System.out.println("Притулок порожній.");
            return;
        }

        showAllPets();
        System.out.print("Виберіть номер тварини для видалення: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index >= 0 && index < pets.size()) {
            pets.remove(index);
            System.out.println("Тварина була видалена з притулку.");
        } else {
            System.out.println("Невірний номер.");
        }
    }


    private List<Pet> loadPets() {
        try {
            File file = new File(DATA_FILE);
            if (file.exists()) {
                return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Pet.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    private void savePets() {
        try {
            objectMapper.writeValue(new File(DATA_FILE), pets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
