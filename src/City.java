class City {
    String name;
    int population;

    public City(String name, int population) {
        this.name = name;
        this.population = population;
    }

    @Override
    public String toString() {
        return "City{" + "name='" + name + '\'' + ", population=" + population + '}';
    }
}

class Road {
    int roadNumber;
    String roadClass;

    public Road(int roadNumber, String roadClass) {
        this.roadNumber = roadNumber;
        this.roadClass = roadClass;
    }

    @Override
    public String toString() {
        return "Road{" + "roadNumber=" + roadNumber + ", roadClass='" + roadClass + '\'' + '}';
    }
}
