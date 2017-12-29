package kseg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class RecommendationEngine {

    private final Matrix matrix;
    private final int userIndex;
    private final int noOfItemsToBeRecommended;
    private final Vectors.DistanceAlgorithm similarityAlgorithm;
    private final ScoreEstimater scoreEstimator;

    public RecommendationEngine(Matrix matrix, int userIndex, int n, Vectors.DistanceAlgorithm similarityAlgorithm, ScoreEstimater scoreEstimater) {
        this.matrix = matrix;
        this.userIndex = userIndex;
        this.noOfItemsToBeRecommended = n;
        this.similarityAlgorithm = similarityAlgorithm;
        this.scoreEstimator = scoreEstimater;
    }

    public List recommend() {
        int unRatedItems[] = findUnratedItems(matrix, userIndex);

        if(0 == unRatedItems.length) {
            return new ArrayList();
        }

        for(int unratedItemIndex : unRatedItems) {
            double score = scoreEstimator.estimate(matrix, userIndex, similarityAlgorithm, unratedItemIndex);
            System.out.println("Score " + unratedItemIndex + ": " + score);
        }
    }

    private int[] findUnratedItems(Matrix matrix, int userIndex) {
        double userItems[] = matrix.getRow(userIndex);
        return IntStream.range(0, userItems.length)
                .filter(i -> userItems[i] != 0)
                .toArray();
    }

    public static final ScoreEstimater stdScoreEstimator = new ScoreEstimater() {
        @Override
        public double estimate(Matrix matrix, int userIndex, Vectors.DistanceAlgorithm similarityAlgorithm, int itemIndex) {
            double similarityTotal = 0;
            double ratSimilarityTotal = 0;
            for(int j = 0; j < matrix.colDimensions(); j++) {
                double userRating = matrix.getElement(userIndex, j);
                if(0 == userRating) continue;
                int ovLapItemIndices[] = findOverLappingIndices(matrix.getColumn(itemIndex), matrix.getColumn(j));

                double similarity = 0;

                if(0 == ovLapItemIndices.length) {
                    similarity = 0;
                } else {
                    similarity = Vectors.similarity(matrix.getColumn(ovLapItemIndices, itemIndex), matrix.getColumn(ovLapItemIndices, j), similarityAlgorithm);
                }

                similarityTotal += similarity;

                ratSimilarityTotal += similarity * userRating;

            }

            return 0 == similarityTotal ? 0 : ratSimilarityTotal/similarityTotal;
        }
    };

    private static int[] findOverLappingIndices(double[] column, double[] column1) {
        return IntStream.range(0, column.length)
                .filter(i -> column[i] > 0 && column1[i] > 0)
                .toArray();
    }


    public static class Builder {

        private Matrix matrix;
        private int userIndex;
        private int noOfItemsToBeRecommended;
        private Vectors.DistanceAlgorithm similarityAlgorithm;
        private ScoreEstimater scoreEstimator;


        public void withMatrix(Matrix matrix) {
            this.matrix = matrix;
        }

        public void withUserIndex(int userIndex) {
            this.userIndex = userIndex;
        }

        public void withNoOfItemsToBeRecommended(int noOfItemsToBeRecommended) {
            this.noOfItemsToBeRecommended = noOfItemsToBeRecommended;
        }

        public void withSimilarityAlgorithm(Vectors.DistanceAlgorithm similarityAlgorithm) {
            this.similarityAlgorithm = similarityAlgorithm;
        }

        public void withScoreEstimator(ScoreEstimater scoreEstimator) {
            this.scoreEstimator = scoreEstimator;
        }

        public RecommendationEngine build() {
            return new RecommendationEngine(matrix, userIndex, noOfItemsToBeRecommended, similarityAlgorithm, scoreEstimator);
        }
    }


    @FunctionalInterface
    public interface ScoreEstimater {
        double estimate(Matrix matrix, int userIndex, Vectors.DistanceAlgorithm similarityAlgorithm, int itemIndex);
    }

}
