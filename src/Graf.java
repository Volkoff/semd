import java.util.*;

class Graf {
    private Table<String, City> vertices; // Cities
    private Table<String, Road> edges;   // Roads (edges between cities)

    public Graf() {
        vertices = new Table<>();
        edges = new Table<>();
    }

    // Add a city (vertex)
    public void addVertex(String cityName, int population) {
        City city = new City(cityName, population);
        vertices.insert(cityName, city);
    }

    // Find a city (vertex)
    public City findVertex(String cityName) {
        return vertices.find(cityName);
    }

    // Remove a city (vertex)
    public void removeVertex(String cityName) {
        vertices.remove(cityName);
    }

    // Add a road (edge between two cities)
    public void addEdge(String city1, String city2, int roadNumber, String roadClass) {
        Road road = new Road(roadNumber, roadClass);
        edges.insert(city1 + "-" + city2, road);
    }

    // Find a road (edge)
    public Road findEdge(String city1, String city2) {
        return edges.find(city1 + "-" + city2);
    }

    // Remove a road (edge)
    public void removeEdge(String city1, String city2) {
        edges.remove(city1 + "-" + city2);
    }

    // DFS (Depth-First Search)
    public void dfs(String startCity) {
        Set<String> visited = new HashSet<>();
        dfsHelper(startCity, visited);
    }

    private void dfsHelper(String city, Set<String> visited) {
        if (!visited.contains(city)) {
            System.out.println(city);
            visited.add(city);
            for (String neighbor : getNeighbors(city)) {
                dfsHelper(neighbor, visited);
            }
        }
    }

    // BFS (Breadth-First Search)
    public void bfs(String startCity) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(startCity);
        visited.add(startCity);

        while (!queue.isEmpty()) {
            String city = queue.poll();
            System.out.println(city);
            for (String neighbor : getNeighbors(city)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }

    // Get neighbors (cities connected by roads)
    private List<String> getNeighbors(String city) {
        List<String> neighbors = new ArrayList<>();
        for (Table.Entry<String, Road> entry : edges.data) {
            String edgeKey = entry.key;
            if (edgeKey.startsWith(city + "-")) {
                neighbors.add(edgeKey.split("-")[1]);
            } else if (edgeKey.endsWith("-" + city)) {
                neighbors.add(edgeKey.split("-")[0]);
            }
        }
        return neighbors;
    }
}

