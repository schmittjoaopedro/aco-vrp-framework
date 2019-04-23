package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.vrp.pdptw_lns.Solver;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class PDPTW_LNS_Test {

    private static final String pdptw100Directory;

    private static final String pdptw200Directory;

    static {
        pdptw100Directory = Paths.get(PDPTW_LNS_Test.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
        pdptw200Directory = Paths.get(PDPTW_LNS_Test.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
    }

    @Test
    public void pdptw_lc104_test() {
        String problem = "lc104.txt";
        Solver solver = new Solver(pdptw100Directory, problem, 1);
        solver.run();
        List<String> logs = solver.getLogs();
        assertThat(logs.get(0)).isEqualTo("Executing lc104.txt");
        assertThat(logs.get(1)).isEqualTo("Test run #0");
        assertThat(logs.get(2)).isEqualTo("Stage 1 : Minimize number of vehicles used");
        assertThat(logs.get(3)).isEqualTo("Min Vehicle User : 10 Best Solution: 1276.1069806913388");
        assertThat(logs.get(4)).isEqualTo("Min Vehicle User : 9 Best Solution: 1219.2908485300784");
        assertThat(logs.get(5)).isEqualTo("Stage 2 : Minimize the cost");
        assertThat(logs.get(6)).isEqualTo("Min Vehicle User : 9 Best Solution: 860.0111824107878");
        assertThat(logs.get(8)).contains("Vehicle 1: 0 90 87 86 83 82 84 85 88 89 91 0 \n" +
                "Vehicle 2: 0 81 78 76 71 70 73 77 79 80 63 0 \n" +
                "Vehicle 3: 0 67 65 62 74 72 61 64 68 66 69 0 \n" +
                "Vehicle 4: 0 13 17 19 16 15 18 28 23 26 30 22 21 0 \n" +
                "Vehicle 5: 0 5 3 7 8 11 9 6 4 2 1 75 106 0 \n" +
                "Vehicle 6: 0 57 55 54 53 56 58 60 59 40 46 43 41 0 \n" +
                "Vehicle 7: 0 20 25 42 44 45 48 51 50 52 49 47 105 0 \n" +
                "Vehicle 8: 0 27 32 33 103 31 35 38 37 39 36 34 29 24 101 0 \n" +
                "Vehicle 9: 0 98 96 95 94 92 104 93 97 100 99 12 14 102 10 0");
    }

    @Test
    public void pdptw_lr110_test() {
        String problem = "lr110.txt";
        Solver solver = new Solver(pdptw100Directory, problem, 1);
        solver.run();
        List<String> logs = solver.getLogs();
        assertThat(logs.get(0)).isEqualTo("Executing lr110.txt");
        assertThat(logs.get(1)).isEqualTo("Test run #0");
        assertThat(logs.get(2)).isEqualTo("Stage 1 : Minimize number of vehicles used");
        assertThat(logs.get(3)).isEqualTo("Min Vehicle User : 13 Best Solution: 1402.9855071494655");
        assertThat(logs.get(4)).isEqualTo("Min Vehicle User : 12 Best Solution: 1393.3012535372611");
        assertThat(logs.get(5)).isEqualTo("Min Vehicle User : 11 Best Solution: 1272.16761703164");
        assertThat(logs.get(6)).isEqualTo("Min Vehicle User : 10 Best Solution: 1159.3484062803186");
        assertThat(logs.get(7)).isEqualTo("Stage 2 : Minimize the cost");
        assertThat(logs.get(8)).isEqualTo("Min Vehicle User : 10 Best Solution: 1159.3484062803186");
        assertThat(logs.get(10)).contains("ehicle 1: 0 52 88 7 82 18 8 46 48 0 \n" +
                "Vehicle 2: 0 62 11 19 47 36 49 64 32 102 70 0 \n" +
                "Vehicle 3: 0 30 71 9 51 81 34 35 33 103 1 0 \n" +
                "Vehicle 4: 0 95 14 44 38 86 17 84 45 60 89 0 \n" +
                "Vehicle 5: 0 2 41 22 74 73 40 53 26 54 24 0 \n" +
                "Vehicle 6: 0 21 72 75 56 23 67 39 25 55 4 0 \n" +
                "Vehicle 7: 0 27 69 31 63 90 10 101 20 66 65 0 \n" +
                "Vehicle 8: 0 83 5 16 61 6 94 96 97 37 100 91 93 0 \n" +
                "Vehicle 9: 0 92 59 99 98 85 87 57 15 43 42 13 58 0 \n" +
                "Vehicle 10: 0 28 12 76 29 78 79 3 50 77 104 68 80 0 ");
    }

    @Test
    public void pdptw_lr1_2_2_test() {
        String problem = "lr1_2_2.txt";
        Solver solver = new Solver(pdptw200Directory, problem, 1);
        solver.run();
        List<String> logs = solver.getLogs();
        assertThat(logs.get(0)).isEqualTo("Executing lr1_2_2.txt");
        assertThat(logs.get(1)).isEqualTo("Test run #0");
        assertThat(logs.get(2)).isEqualTo("Stage 1 : Minimize number of vehicles used");
        assertThat(logs.get(3)).isEqualTo("Min Vehicle User : 22 Best Solution: 6256.284818945369");
        assertThat(logs.get(4)).isEqualTo("Min Vehicle User : 21 Best Solution: 6105.934977387091");
        assertThat(logs.get(5)).isEqualTo("Min Vehicle User : 20 Best Solution: 5819.388105691053");
        assertThat(logs.get(6)).isEqualTo("Min Vehicle User : 19 Best Solution: 5340.118129371615");
        assertThat(logs.get(7)).isEqualTo("Min Vehicle User : 18 Best Solution: 5020.405554005394");
        assertThat(logs.get(8)).isEqualTo("Min Vehicle User : 17 Best Solution: 4793.994243999105");
        assertThat(logs.get(9)).isEqualTo("Stage 2 : Minimize the cost");
        assertThat(logs.get(10)).isEqualTo("Min Vehicle User : 17 Best Solution: 4621.2058468227615");
        assertThat(logs.get(12)).contains("Vehicle 1: 0 158 51 50 66 11 160 126 148 113 119 0 \n" +
                "Vehicle 2: 0 132 18 143 176 69 31 134 103 101 154 0 \n" +
                "Vehicle 3: 0 168 92 210 48 169 187 78 139 185 81 0 \n" +
                "Vehicle 4: 0 46 15 71 186 166 151 180 167 56 61 0 \n" +
                "Vehicle 5: 0 63 44 3 86 131 79 156 172 14 197 0 \n" +
                "Vehicle 6: 0 87 161 115 16 162 171 170 122 42 209 146 149 0 \n" +
                "Vehicle 7: 0 157 93 199 135 193 175 39 37 179 190 206 72 0 \n" +
                "Vehicle 8: 0 35 32 90 95 173 138 29 96 43 191 141 110 0 \n" +
                "Vehicle 9: 0 145 192 53 2 8 76 17 40 121 60 208 105 0 \n" +
                "Vehicle 10: 0 196 194 20 163 104 100 136 142 159 13 10 198 0 \n" +
                "Vehicle 11: 0 73 30 128 34 98 6 189 177 102 205 77 127 58 114 0 \n" +
                "Vehicle 12: 0 140 57 152 38 62 84 118 99 88 183 200 201 112 45 0 \n" +
                "Vehicle 13: 0 106 85 65 94 12 19 178 23 22 47 82 207 74 108 0 \n" +
                "Vehicle 14: 0 4 188 184 153 54 164 89 125 129 33 144 70 107 7 0 \n" +
                "Vehicle 15: 0 155 67 150 137 68 64 109 28 182 147 9 174 203 97 0 \n" +
                "Vehicle 16: 0 1 120 26 123 21 165 124 181 111 59 83 204 52 24 0 \n" +
                "Vehicle 17: 0 55 41 36 5 117 133 27 195 49 202 25 116 130 80 91 75 0");
    }
}
