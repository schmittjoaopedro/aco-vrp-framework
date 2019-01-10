package com.github.schmittjoaopedro;

import com.github.schmittjoaopedro.tsp.aco.ls.LocalSearchUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MatrixConversionTest {

    @Test
    public void testMatrixAsymmetricToSymmetric() {
        int M = 1;
        int INF = 25;
        int[][] matrix = {
                {0, 6, 4, 1, 8},
                {9, 0, 10, 14, 7},
                {3, 2, 0, 8, 20},
                {15, 7, 11, 0, 3},
                {25, 10, 4, 2, 0}
        };
        matrix = LocalSearchUtils.asymmetricToSymmetric(matrix, M, INF);
        M = -1; // FOR TEST PURPOSES
        Assertions.assertThat(matrix[0]).containsExactly(INF, INF, INF, INF, INF, M, 9, 3, 15, 25);
        Assertions.assertThat(matrix[1]).containsExactly(INF, INF, INF, INF, INF, 6, M, 2, 7, 10);
        Assertions.assertThat(matrix[2]).containsExactly(INF, INF, INF, INF, INF, 4, 10, M, 11, 4);
        Assertions.assertThat(matrix[3]).containsExactly(INF, INF, INF, INF, INF, 1, 14, 8, M, 2);
        Assertions.assertThat(matrix[4]).containsExactly(INF, INF, INF, INF, INF, 8, 7, 20, 3, M);
        Assertions.assertThat(matrix[5]).containsExactly(M, 6, 4, 1, 8, INF, INF, INF, INF, INF);
        Assertions.assertThat(matrix[6]).containsExactly(9, M, 10, 14, 7, INF, INF, INF, INF, INF);
        Assertions.assertThat(matrix[7]).containsExactly(3, 2, M, 8, 20, INF, INF, INF, INF, INF);
        Assertions.assertThat(matrix[8]).containsExactly(15, 7, 11, M, 3, INF, INF, INF, INF, INF);
        Assertions.assertThat(matrix[9]).containsExactly(25, 10, 4, 2, M, INF, INF, INF, INF, INF);
    }

}
