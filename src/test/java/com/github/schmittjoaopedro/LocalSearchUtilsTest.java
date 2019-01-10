package com.github.schmittjoaopedro;

import com.github.schmittjoaopedro.tsp.aco.ls.LocalSearchUtils;
import com.github.schmittjoaopedro.tsp.aco.ls.opt3.asymmetric.Asymmetric3Opt;
import com.github.schmittjoaopedro.tsp.utils.Maths;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class LocalSearchUtilsTest {

    @Test
    public void testMatrixLocalSearchUtils() {
        int M, INF;
        int[][] symmetricMatrix;
        int[][] asymmetricMatrix = {
                {0, 6, 4, 1, 8},
                {9, 0, 10, 14, 7},
                {3, 2, 0, 8, 20},
                {15, 7, 11, 0, 3},
                {25, 10, 4, 2, 0}
        };
        M = Maths.getValueByInequality(asymmetricMatrix, Math::min);
        INF = Maths.getValueByInequality(asymmetricMatrix, Math::max);
        INF++; // The INF value must be greater than all values in the symmetric matrix
        symmetricMatrix = LocalSearchUtils.asymmetricToSymmetric(asymmetricMatrix, M, INF);
        // Check if the conversion of symmetric to asymmetric is working
        Assertions.assertThat(symmetricMatrix[0]).containsExactly(INF, INF, INF, INF, INF, M, 9, 3, 15, 25);
        Assertions.assertThat(symmetricMatrix[1]).containsExactly(INF, INF, INF, INF, INF, 6, M, 2, 7, 10);
        Assertions.assertThat(symmetricMatrix[2]).containsExactly(INF, INF, INF, INF, INF, 4, 10, M, 11, 4);
        Assertions.assertThat(symmetricMatrix[3]).containsExactly(INF, INF, INF, INF, INF, 1, 14, 8, M, 2);
        Assertions.assertThat(symmetricMatrix[4]).containsExactly(INF, INF, INF, INF, INF, 8, 7, 20, 3, M);
        Assertions.assertThat(symmetricMatrix[5]).containsExactly(M, 6, 4, 1, 8, INF, INF, INF, INF, INF);
        Assertions.assertThat(symmetricMatrix[6]).containsExactly(9, M, 10, 14, 7, INF, INF, INF, INF, INF);
        Assertions.assertThat(symmetricMatrix[7]).containsExactly(3, 2, M, 8, 20, INF, INF, INF, INF, INF);
        Assertions.assertThat(symmetricMatrix[8]).containsExactly(15, 7, 11, M, 3, INF, INF, INF, INF, INF);
        Assertions.assertThat(symmetricMatrix[9]).containsExactly(25, 10, 4, 2, M, INF, INF, INF, INF, INF);

        // Check if the NN_LIST creator is working
        int nnLength = 5;
        int nnList[][] = LocalSearchUtils.createNNList(symmetricMatrix.length, nnLength, symmetricMatrix);
        // Check if the indexes of the min values are in ascending order
        Assertions.assertThat(nnList[0]).containsExactly(5, 7, 6, 8, 9);
        Assertions.assertThat(nnList[1]).containsExactly(6, 7, 5, 8, 9);
        Assertions.assertThat(nnList[2]).containsExactly(7, 5, 9, 6, 8);
        Assertions.assertThat(nnList[3]).containsExactly(8, 5, 9, 7, 6);
        Assertions.assertThat(nnList[4]).containsExactly(9, 8, 6, 5, 7);
        Assertions.assertThat(nnList[5]).containsExactly(0, 3, 2, 1, 4);
        Assertions.assertThat(nnList[6]).containsExactly(1, 4, 0, 2, 3);
        Assertions.assertThat(nnList[7]).containsExactly(2, 1, 0, 3, 4);
        Assertions.assertThat(nnList[8]).containsExactly(3, 4, 1, 2, 0);
        Assertions.assertThat(nnList[9]).containsExactly(4, 3, 2, 1, 0);

        // Check the symmetric to asymmetric tour conversion
        int[] asymmetricOriginalTour = {0, 1, 2, 3, 4, 0};
        int[] symmetricOriginalTour = {0, 5, 1, 6, 2, 7, 3, 8, 4, 9, 0};
        Assertions.assertThat(LocalSearchUtils.getTourIgnoreIdxGreaterThan(symmetricOriginalTour, 5)).containsExactly(asymmetricOriginalTour);

        // Check if the 3-opt is working with asymmetric matrices
        int[] asymmetricTourOptimized = asymmetricOriginalTour.clone();
        //LocalSearch3Opt.three_opt_first(new Random(1), symmetricTourOptimized, symmetricMatrix.length, symmetricMatrix, nnList, nnLength);
        Asymmetric3Opt.threeOpt(asymmetricTourOptimized, symmetricMatrix);
        Assertions.assertThat(asymmetricTourOptimized).containsExactly(0, 3, 4, 1, 2, 0);
        // Check cost calculation for asymmetric matrix
        Assertions.assertThat(LocalSearchUtils.fitnessEvaluation(asymmetricOriginalTour, asymmetricMatrix)).isEqualTo(52);
        Assertions.assertThat(LocalSearchUtils.fitnessEvaluation(asymmetricTourOptimized, asymmetricMatrix)).isEqualTo(27);
    }

}
