package kseg;

public class TestRecommendationEngine {
    public static void main(String[] args) {
        double dataArr[][] = new double[][] {
                {1,1,1,0,0},
                {2,2,2,0,0},
                {1,1,1,0,0},
                {5,5,5,0,0},
                {1,1,0,2,2},
                {0,0,0,3,3},
                {0,0,0,1,1}
        };

        Matrix data = new Matrix(dataArr);

        System.out.println("----------Euclidean distance and similarity--------------");
        Vectors.DistanceAlgorithm distanceAlgorithm = Vectors.DistanceAlgorithm.EUCLIDEAN;
        double distance = Vectors.distance(data.getColumn(0), data.getColumn(4), distanceAlgorithm);
        System.out.println("distance : " + distance);
        double similarity = Vectors.similarity(data.getColumn(0), data.getColumn(4), distanceAlgorithm);
        System.out.println("similarity : " + similarity);
        similarity = Vectors.similarity(data.getColumn(0), data.getColumn(0), distanceAlgorithm);
        System.out.println("similarity 0-0: " + similarity);
        System.out.println();

        System.out.println("----------Cosine distance and similarity--------------");
        distanceAlgorithm = Vectors.DistanceAlgorithm.COSINE;
        distance = Vectors.distance(data.getColumn(0), data.getColumn(4), distanceAlgorithm);
        System.out.println("distance : " + distance);
        similarity = Vectors.similarity(data.getColumn(0), data.getColumn(4), distanceAlgorithm);
        System.out.println("similarity : " + similarity);
        similarity = Vectors.similarity(data.getColumn(0), data.getColumn(0), distanceAlgorithm);
        System.out.println("similarity 0-0: " + similarity);
        System.out.println();

        System.out.println("----------Pearson correlation distance and similarity--------------");
        distanceAlgorithm = Vectors.DistanceAlgorithm.PEARSON_CORRELATION;
        distance = Vectors.distance(data.getColumn(0), data.getColumn(4), distanceAlgorithm);
        System.out.println("distance : " + distance);
        similarity = Vectors.similarity(data.getColumn(0), data.getColumn(4), distanceAlgorithm);
        System.out.println("similarity : " + similarity);
        similarity = Vectors.similarity(data.getColumn(0), data.getColumn(0), distanceAlgorithm);
        System.out.println("similarity 0-0: " + similarity);
        System.out.println();
    }
}
