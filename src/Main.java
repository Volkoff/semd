import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Graf graf = new Graf();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the road network system!");

        System.out.println("Do you want to load data from a file? (yes/no)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("Enter the path of the file:");
            String filePath = scanner.nextLine();
            loadFromFile(filePath, graf);
        } else {
            loadFromKeyboard(graf, scanner);
        }

        boolean continueOperations = true;
        while (continueOperations) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Perform DFS");
            System.out.println("2. Perform BFS");
            System.out.println("3. Find a city");
            System.out.println("4. Remove a city");
            System.out.println("5. Find a road");
            System.out.println("6. Remove a road");
            System.out.println("7. Exit");

            int operationChoice = scanner.nextInt();
            scanner.nextLine();

            switch (operationChoice) {
                case 1:
                    System.out.println("Enter the starting city for DFS:");
                    String startCityDFS = scanner.nextLine();
                    System.out.println("Performing DFS from " + startCityDFS + ":");
                    graf.dfs(startCityDFS);
                    break;

                case 2:
                    System.out.println("Enter the starting city for BFS:");
                    String startCityBFS = scanner.nextLine();
                    System.out.println("Performing BFS from " + startCityBFS + ":");
                    graf.bfs(startCityBFS);
                    break;

                case 3:
                    System.out.println("Enter the city name to find:");
                    String cityNameToFind = scanner.nextLine();
                    City foundCity = graf.findVertex(cityNameToFind);
                    if (foundCity != null) {
                        System.out.println("City found: " + foundCity);
                    } else {
                        System.out.println("City not found.");
                    }
                    break;

                case 4:
                    System.out.println("Enter the city name to remove:");
                    String cityNameToRemove = scanner.nextLine();
                    graf.removeVertex(cityNameToRemove);
                    System.out.println("City " + cityNameToRemove + " removed.");
                    break;

                case 5:
                    System.out.println("Enter the cities to find a road (e.g., Prague Brno):");
                    String roadQuery = scanner.nextLine();
                    String[] cities = roadQuery.split("\\s+");
                    if (cities.length == 2) {
                        Road foundRoad = graf.findEdge(cities[0], cities[1]);
                        if (foundRoad != null) {
                            System.out.println("Road found: " + foundRoad);
                        } else {
                            System.out.println("Road not found.");
                        }
                    } else {
                        System.out.println("Invalid input.");
                    }
                    break;

                case 6:
                    System.out.println("Enter the cities to remove a road (e.g., Prague Brno):");
                    String roadToRemoveQuery = scanner.nextLine();
                    String[] citiesToRemove = roadToRemoveQuery.split("\\s+");
                    if (citiesToRemove.length == 2) {
                        graf.removeEdge(citiesToRemove[0], citiesToRemove[1]);
                        System.out.println("Road between " + citiesToRemove[0] + " and " + citiesToRemove[1] + " removed.");
                    } else {
                        System.out.println("Invalid input.");
                    }
                    break;

                case 7:
                    continueOperations = false;
                    System.out.println("Exiting the program.");
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    public static void loadFromFile(String filePath, Graf graf) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean readingCities = true;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (line.startsWith("#")) {
                    continue;
                }

                if (line.equalsIgnoreCase("Cities")) {
                    readingCities = true;
                    continue;
                } else if (line.equalsIgnoreCase("Roads")) {
                    readingCities = false;
                    continue;
                }

                String[] parts = line.split("\\s+");

                if (parts.length < 2) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                if (readingCities) {
                    if (parts.length == 2) {
                        String cityName = parts[0];
                        try {
                            int population = Integer.parseInt(parts[1]);
                            graf.addVertex(cityName, population);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid population number for city: " + parts[1]);
                        }
                    }
                } else {
                    if (parts.length == 4) {
                        String city1 = parts[0];
                        String city2 = parts[1];
                        try {
                            int roadNumber = Integer.parseInt(parts[2]);
                            String roadClass = parts[3];
                            graf.addEdge(city1, city2, roadNumber, roadClass);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid road number for road: " + parts[2]);
                        }
                    } else {
                        System.out.println("Skipping invalid road data: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }


    public static void loadFromKeyboard(Graf graf, Scanner scanner) {
        System.out.println("Enter number of cities:");
        int cityCount = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < cityCount; i++) {
            System.out.println("Enter city name and population (e.g., Prague 1300000):");
            String cityData = scanner.nextLine();
            String[] cityParts = cityData.split("\\s+");
            String cityName = cityParts[0];
            int population = Integer.parseInt(cityParts[1]);
            graf.addVertex(cityName, population);
        }

        System.out.println("Enter number of roads:");
        int roadCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < roadCount; i++) {
            System.out.println("Enter road data (e.g., Prague Brno 1 D1):");
            String roadData = scanner.nextLine();
            String[] roadParts = roadData.split("\\s+");
            String city1 = roadParts[0];
            String city2 = roadParts[1];
            int roadNumber = Integer.parseInt(roadParts[2]);
            String roadClass = roadParts[3];
            graf.addEdge(city1, city2, roadNumber, roadClass);
        }
    }
}
