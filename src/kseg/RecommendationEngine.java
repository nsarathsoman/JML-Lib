package kseg;

import java.util.ArrayList;
import java.util.List;

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
        return new ArrayList();
    }

    public static final ScoreEstimater stdScoreEstimator = new ScoreEstimater() {
        @Override
        public double estimate(Matrix matrix, int userIndex, Vectors.DistanceAlgorithm similarityAlgorithm, int itemIndex) {
            return 0;
        }
    };



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
