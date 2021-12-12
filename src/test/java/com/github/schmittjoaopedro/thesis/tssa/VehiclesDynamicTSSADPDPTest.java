package com.github.schmittjoaopedro.thesis.tssa;

import com.github.schmittjoaopedro.vrp.thesis.ALNS_DPDP;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.LNSOptimizer;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.stop.TimeCriteria;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.tabu_sa.TABU_SA_DPDP;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class VehiclesDynamicTSSADPDPTest {

    private static int maxIterations = 25000;

    private static String dpdptw100Directory;

    private static String dpdptw400Directory;

    private static String dpdptw800Directory;

    static {
        try {
            dpdptw100Directory = Paths.get(VehiclesDynamicTSSADPDPTest.class.getClassLoader().getResource("dpdptw_100").toURI()).toString();
            dpdptw400Directory = Paths.get(VehiclesDynamicTSSADPDPTest.class.getClassLoader().getResource("dpdptw_400").toURI()).toString();
            dpdptw800Directory = Paths.get(VehiclesDynamicTSSADPDPTest.class.getClassLoader().getResource("dpdptw_800").toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dpdptw_lc103_a_0_1_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc103_a_0.1.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\projetos\\aco-vrp-framework\\docs\\vehicle-tracker-animated\\public\\log", 0.005);
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.tours.size()).isEqualTo(9);
        assertThat(solutionBest.totalCost).isEqualTo(1091.9012674900193);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(10351.528796652874);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(259.62752916285507);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 25 27 29 30 45 50 23 22 28 21 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 5 3 7 8 10 11 9 6 4 2 1 75 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 42 41 40 44 46 101 72 61 68 66 48 49 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 90 87 62 74 84 85 88 86 89 91 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 32 33 31 35 104 37 38 39 36 34 26 103 20 24 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 13 17 18 19 15 16 14 12 43 51 52 47 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 67 65 98 94 92 93 102 97 100 99 96 95 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 57 55 54 53 56 58 64 60 59 69 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 81 78 76 71 70 73 77 79 80 83 82 63 0");
    }

    @Test
    public void dpdptw_lc103_a_0_5_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc103_a_0.5.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\projetos\\aco-vrp-framework\\docs\\vehicle-tracker-animated\\public\\log", 0.005);
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.tours.size()).isEqualTo(10);
        assertThat(solutionBest.totalCost).isEqualTo(1122.899304693011);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(10895.396622947317);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(772.4973182543056);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 67 65 25 27 30 26 103 23 22 20 24 21 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 42 41 40 44 43 45 48 51 50 52 49 47 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 87 90 98 92 84 88 83 82 86 89 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 32 33 31 35 104 37 38 39 36 34 29 28 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 55 59 62 74 72 61 64 68 66 69 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 13 17 19 15 14 97 100 18 16 12 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 81 78 76 70 71 77 79 80 73 63 0");
        assertThat(summaryResults).contains("Start time 16.0 Route = 0 3 5 7 8 10 11 9 6 4 2 1 75 0");
        assertThat(summaryResults).contains("Start time 60.0 Route = 0 54 57 53 56 58 60 46 101 0");
        assertThat(summaryResults).contains("Start time 147.0 Route = 0 93 102 94 96 99 95 85 91 0");
    }

    @Test
    public void dpdptw_lc103_q_0_0_1_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc103_q_0_0.1.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\projetos\\aco-vrp-framework\\docs\\vehicle-tracker-animated\\public\\log", 0.005);
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.tours.size()).isEqualTo(17);
        assertThat(solutionBest.totalCost).isEqualTo(1535.8441578022364);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(11944.275901534264);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(1408.4317437320271);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 67 65 62 74 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 13 17 19 15 14 12 94 99 96 95 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 32 33 37 35 104 31 38 39 36 34 18 16 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 81 78 76 70 77 79 80 71 73 63 0");
        assertThat(summaryResults).contains("Start time 28.0 Route = 0 87 88 61 66 83 82 0");
        assertThat(summaryResults).contains("Start time 32.0 Route = 0 55 57 53 59 0");
        assertThat(summaryResults).contains("Start time 39.0 Route = 0 42 25 27 30 23 22 21 49 46 101 0");
        assertThat(summaryResults).contains("Start time 123.0 Route = 0 3 8 5 11 0");
        assertThat(summaryResults).contains("Start time 126.0 Route = 0 41 40 44 48 45 72 68 50 52 47 0");
        assertThat(summaryResults).contains("Start time 210.0 Route = 0 54 56 58 60 64 69 43 51 0");
        assertThat(summaryResults).contains("Start time 216.0 Route = 0 98 92 93 102 97 100 0");
        assertThat(summaryResults).contains("Start time 303.0 Route = 0 10 7 9 6 4 2 85 91 0");
        assertThat(summaryResults).contains("Start time 311.0 Route = 0 90 84 0");
        assertThat(summaryResults).contains("Start time 919.83 Route = 0 86 89 0");
        assertThat(summaryResults).contains("Start time 922.0 Route = 0 29 28 26 103 0");
        assertThat(summaryResults).contains("Start time 928.0 Route = 0 1 75 0");
        assertThat(summaryResults).contains("Start time 936.0 Route = 0 20 24 0");
    }

    @Test
    public void dpdptw_lc103_q_0_0_5_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc103_q_0_0.5.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\projetos\\aco-vrp-framework\\docs\\vehicle-tracker-animated\\public\\log", 0.005);
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.tours.size()).isEqualTo(15);
        assertThat(solutionBest.totalCost).isEqualTo(1316.830143348303);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(11356.986855428031);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(1040.1567120797285);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 5 3 7 8 10 11 9 6 4 2 85 91 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 42 41 40 44 48 45 72 68 50 49 46 101 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 32 33 35 104 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 67 65 61 62 74 66 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 57 55 54 53 56 58 60 59 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 13 17 18 19 15 16 14 12 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 81 78 76 70 77 79 80 71 73 63 0");
        assertThat(summaryResults).contains("Start time 28.0 Route = 0 87 96 95 90 84 88 83 82 86 89 0");
        assertThat(summaryResults).contains("Start time 118.0 Route = 0 25 27 29 30 28 26 103 23 22 21 52 47 0");
        assertThat(summaryResults).contains("Start time 216.0 Route = 0 98 92 93 102 97 100 0");
        assertThat(summaryResults).contains("Start time 297.0 Route = 0 31 37 38 39 36 34 0");
        assertThat(summaryResults).contains("Start time 581.0 Route = 0 64 69 43 51 0");
        assertThat(summaryResults).contains("Start time 589.0 Route = 0 94 99 0");
        assertThat(summaryResults).contains("Start time 928.0 Route = 0 1 75 0");
        assertThat(summaryResults).contains("Start time 936.0 Route = 0 20 24 0");
    }

    @Test
    public void dpdptw_lr1_4_8_q_0_0_1_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw400Directory, "LR1_4_8_q_0_0.1.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\projetos\\aco-vrp-framework\\docs\\vehicle-tracker-animated\\public\\log", 0.005);
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.tours.size()).isEqualTo(43);
        assertThat(solutionBest.totalCost).isEqualTo(11715.95058167183);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(17378.900993485535);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(1662.950411813708);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 30 343 6 62 264 198 202 53 370 17 205 308 149 14 101 355 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 304 84 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 164 72 377 294 113 169 142 344 215 338 214 162 92 66 387 254 356 365 371 374 402 273 413 362 243 386 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 291 329 307 34 260 394 349 228 105 111 358 272 306 384 364 49 221 305 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 389 292 316 331 300 93 123 245 124 108 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 227 28 290 64 154 158 115 242 381 340 253 153 398 326 186 76 25 354 257 15 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 218 346 173 87 378 230 151 161 269 199 63 183 234 385 103 393 339 262 156 341 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 190 16 380 309 112 296 299 174 231 239 100 74 129 366 128 102 255 3 120 404 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 302 163 90 184 4 207 29 144 408 20 317 372 208 352 176 235 71 159 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 155 328 246 196 99 114 32 139 238 237 333 23 22 295 132 301 265 417 350 293 37 407 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 216 375 278 263 110 134 67 21 363 126 135 297 283 345 383 50 201 152 419 195 0");
        assertThat(summaryResults).contains("Start time 19.0 Route = 0 303 421 388 182 175 409 325 125 280 261 78 412 0");
        assertThat(summaryResults).contains("Start time 26.85 Route = 0 379 65 79 52 232 391 259 225 81 249 210 361 415 222 75 13 188 416 0");
        assertThat(summaryResults).contains("Start time 134.0 Route = 0 7 94 189 197 323 137 165 140 252 200 83 88 0");
        assertThat(summaryResults).contains("Start time 141.0 Route = 0 86 172 276 401 130 258 0");
        assertThat(summaryResults).contains("Start time 144.0 Route = 0 314 89 180 91 0");
        assertThat(summaryResults).contains("Start time 223.0 Route = 0 310 136 267 69 122 250 8 73 26 56 240 141 0");
        assertThat(summaryResults).contains("Start time 474.0 Route = 0 275 5 360 268 211 117 39 145 0");
        assertThat(summaryResults).contains("Start time 475.0 Route = 0 80 1 332 18 12 403 248 406 58 422 0");
        assertThat(summaryResults).contains("Start time 481.0 Route = 0 68 36 77 147 193 194 181 411 0");
        assertThat(summaryResults).contains("Start time 483.0 Route = 0 320 247 390 373 0");
        assertThat(summaryResults).contains("Start time 485.0 Route = 0 321 271 223 312 0");
        assertThat(summaryResults).contains("Start time 494.0 Route = 0 131 97 322 70 229 24 10 116 0");
        assertThat(summaryResults).contains("Start time 496.0 Route = 0 38 96 219 420 209 9 405 178 104 337 0");
        assertThat(summaryResults).contains("Start time 498.0 Route = 0 192 285 396 382 187 166 0");
        assertThat(summaryResults).contains("Start time 499.0 Route = 0 42 266 191 57 334 107 376 410 0");
        assertThat(summaryResults).contains("Start time 501.0 Route = 0 185 324 177 148 0");
        assertThat(summaryResults).contains("Start time 502.0 Route = 0 392 157 348 351 256 414 241 118 0");
        assertThat(summaryResults).contains("Start time 505.88 Route = 0 82 167 31 399 327 369 217 203 0");
        assertThat(summaryResults).contains("Start time 517.0 Route = 0 277 55 212 286 61 46 0");
        assertThat(summaryResults).contains("Start time 518.0 Route = 0 127 367 171 45 400 43 0");
        assertThat(summaryResults).contains("Start time 521.0 Route = 0 44 204 279 59 2 121 289 35 0");
        assertThat(summaryResults).contains("Start time 533.0 Route = 0 27 160 119 41 47 33 0");
        assertThat(summaryResults).contains("Start time 543.0 Route = 0 330 170 233 281 298 418 0");
        assertThat(summaryResults).contains("Start time 547.0 Route = 0 315 357 19 318 251 270 0");
        assertThat(summaryResults).contains("Start time 555.0 Route = 0 397 284 220 224 0");
        assertThat(summaryResults).contains("Start time 560.0 Route = 0 85 206 319 335 0");
        assertThat(summaryResults).contains("Start time 564.0 Route = 0 336 98 368 95 146 133 0");
        assertThat(summaryResults).contains("Start time 573.0 Route = 0 311 51 179 60 54 40 11 226 0");
        assertThat(summaryResults).contains("Start time 578.0 Route = 0 353 236 143 313 342 213 0");
        assertThat(summaryResults).contains("Start time 581.0 Route = 0 150 48 282 274 395 359 0");
        assertThat(summaryResults).contains("Start time 599.0 Route = 0 168 287 347 106 0");
        assertThat(summaryResults).contains("Start time 606.0 Route = 0 244 109 288 138 0");
    }

    @Test
    public void dpdptw_lc104_a_1_0_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc104_a_1.0.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\projetos\\aco-vrp-framework\\docs\\vehicle-tracker-animated\\public\\log", 0.005);
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isFalse(); // Isn't feasible because were required more vehicles than the problem has available
        assertThat(solutionBest.tours.size()).isEqualTo(29);
        assertThat(solutionBest.totalCost).isEqualTo(2455.693027310761);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(12049.83556856313);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(594.1425412523703);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 67 55 53 54 58 60 59 68 80 63 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 90 87 76 82 84 77 79 73 78 70 47 105 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 13 16 19 15 11 97 100 6 83 88 0");
        assertThat(summaryResults).contains("Start time 28.0 Route = 0 20 25 62 44 48 69 45 51 0");
        assertThat(summaryResults).contains("Start time 123.0 Route = 0 3 8 31 38 28 23 0");
        assertThat(summaryResults).contains("Start time 212.0 Route = 0 65 74 0");
        assertThat(summaryResults).contains("Start time 306.0 Route = 0 92 104 5 9 7 4 81 71 0");
        assertThat(summaryResults).contains("Start time 589.0 Route = 0 94 99 0");
        assertThat(summaryResults).contains("Start time 876.0 Route = 0 57 56 0");
        assertThat(summaryResults).contains("Start time 878.0 Route = 0 95 93 0");
        assertThat(summaryResults).contains("Start time 886.0 Route = 0 32 33 103 37 0");
        assertThat(summaryResults).contains("Start time 889.0 Route = 0 12 10 0");
        assertThat(summaryResults).contains("Start time 892.0 Route = 0 98 96 0");
        assertThat(summaryResults).contains("Start time 893.0 Route = 0 17 18 0");
        assertThat(summaryResults).contains("Start time 894.0 Route = 0 36 34 0");
        assertThat(summaryResults).contains("Start time 897.83 Route = 0 35 39 0");
        assertThat(summaryResults).contains("Start time 905.0 Route = 0 85 89 0");
        assertThat(summaryResults).contains("Start time 908.0 Route = 0 86 91 0");
        assertThat(summaryResults).contains("Start time 915.0 Route = 0 72 64 0");
        assertThat(summaryResults).contains("Start time 916.0 Route = 0 42 52 0");
        assertThat(summaryResults).contains("Start time 918.0 Route = 0 40 46 0");
        assertThat(summaryResults).contains("Start time 919.0 Route = 0 50 49 0");
        assertThat(summaryResults).contains("Start time 920.0 Route = 0 61 66 0");
        assertThat(summaryResults).contains("Start time 923.0 Route = 0 30 21 0");
        assertThat(summaryResults).contains("Start time 924.0 Route = 0 2 1 75 106 0");
        assertThat(summaryResults).contains("Start time 925.0 Route = 0 27 29 24 101 0");
        assertThat(summaryResults).contains("Start time 926.0 Route = 0 43 41 0");
        assertThat(summaryResults).contains("Start time 932.0 Route = 0 26 22 0");
        assertThat(summaryResults).contains("Start time 976.0 Route = 0 14 102 0");
    }

    @Test
    public void dpdptw_lrc2_8_7_q_0_1_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw800Directory, "LRC2_8_7_q_0_0.1.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.enableVehicleControlCenter();
        //solver.enablePrintOperation("C:\\projetos\\aco-vrp-framework\\docs\\vehicle-tracker-animated\\public\\log", 0.005);
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue(); // Isn't feasible because were required more vehicles than the problem has available
        assertThat(solutionBest.tours.size()).isEqualTo(41);
        assertThat(solutionBest.totalCost).isEqualTo(45397.37716306285);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(145821.75041095386);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(92424.37324789098);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 398 701 244 272 431 585 166 438 487 183 529 539 253 146 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 640 73 175 764 368 652 450 149 385 463 697 727 323 10 598 15 347 228 182 134 164 109 232 188 118 517 384 292 566 786 333 150 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 288 432 534 163 2 395 303 14 26 96 661 151 762 265 128 383 533 592 757 204 192 603 753 434 66 81 185 740 794 501 556 418 629 158 624 186 92 429 602 339 108 609 464 180 367 286 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 616 439 36 32 399 547 35 343 478 375 240 174 47 307 749 587 622 693 100 107 20 173 455 612 648 677 498 242 353 430 738 618 1 249 279 574 284 176 217 8 568 396 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 645 45 212 419 494 413 541 213 378 531 0");
        assertThat(summaryResults).contains("Start time 0.0 Route = 0 401 728 354 67 509 717 584 708 668 491 287 516 525 238 248 666 27 512 352 326 127 170 747 601 195 594 688 196 593 136 25 721 205 422 489 442 0");
        assertThat(summaryResults).contains("Start time 102.0 Route = 0 254 772 356 737 391 379 736 320 93 641 54 632 94 177 497 340 689 557 788 538 31 97 626 526 493 371 53 657 226 468 390 63 0");
        assertThat(summaryResults).contains("Start time 115.0 Route = 0 528 421 746 614 310 330 504 719 300 789 155 775 795 296 444 638 0");
        assertThat(summaryResults).contains("Start time 164.0 Route = 0 191 543 162 692 503 582 132 454 325 791 424 485 644 776 135 561 397 765 302 414 113 676 630 112 802 502 377 605 0");
        assertThat(summaryResults).contains("Start time 274.0 Route = 0 99 76 319 301 360 495 156 671 142 752 548 513 105 683 631 147 203 553 441 669 69 759 95 90 207 125 712 569 0");
        assertThat(summaryResults).contains("Start time 294.0 Route = 0 3 437 52 247 346 755 281 222 138 484 532 440 522 129 271 120 542 591 662 779 637 160 315 702 317 283 234 392 663 40 486 467 131 64 91 210 0");
        assertThat(summaryResults).contains("Start time 340.0 Route = 0 316 778 332 758 30 9 211 575 565 646 511 576 732 152 351 571 680 65 361 123 699 726 783 579 98 365 0");
        assertThat(summaryResults).contains("Start time 347.0 Route = 0 412 443 713 549 774 600 55 393 458 435 258 159 409 50 527 225 715 231 490 295 452 564 623 269 627 625 0");
        assertThat(summaryResults).contains("Start time 360.0 Route = 0 725 674 122 42 341 141 471 218 577 604 311 389 208 449 51 620 781 407 327 235 342 433 294 647 28 550 469 181 748 111 0");
        assertThat(summaryResults).contains("Start time 400.0 Route = 0 562 766 130 349 366 635 410 13 6 453 46 4 362 59 530 261 792 299 0");
        assertThat(summaryResults).contains("Start time 420.0 Route = 0 523 480 209 546 7 656 219 337 386 72 733 78 38 634 475 370 115 221 335 552 80 246 79 636 769 427 85 405 241 305 0");
        assertThat(summaryResults).contains("Start time 429.0 Route = 0 690 229 264 660 496 492 573 106 70 777 672 291 767 189 257 282 633 754 465 770 524 224 322 729 148 250 678 33 597 500 0");
        assertThat(summaryResults).contains("Start time 451.0 Route = 0 394 420 165 703 197 691 168 595 245 714 0");
        assertThat(summaryResults).contains("Start time 454.0 Route = 0 161 103 675 470 312 466 796 214 798 588 711 267 695 402 684 665 139 670 153 682 43 417 456 457 0");
        assertThat(summaryResults).contains("Start time 469.0 Route = 0 784 436 145 29 744 314 572 400 86 716 275 473 387 260 739 16 722 723 124 404 262 274 297 797 293 34 555 369 304 5 741 255 321 276 0");
        assertThat(summaryResults).contains("Start time 484.0 Route = 0 704 521 358 309 793 190 559 460 506 686 445 606 520 508 551 87 23 617 88 596 318 642 415 44 0");
        assertThat(summaryResults).contains("Start time 565.0 Route = 0 481 790 515 58 256 613 179 344 628 518 756 382 681 581 619 137 143 510 154 673 216 615 773 477 0");
        assertThat(summaryResults).contains("Start time 576.07 Route = 0 121 334 505 426 724 355 251 760 140 328 277 200 735 372 313 654 259 41 49 709 535 380 0");
        assertThat(summaryResults).contains("Start time 583.0 Route = 0 710 60 411 655 610 763 472 71 110 428 268 586 39 17 499 376 459 658 83 462 611 144 667 178 202 536 298 24 0");
        assertThat(summaryResults).contains("Start time 592.0 Route = 0 133 446 114 373 37 75 62 650 425 350 198 800 233 621 56 698 19 664 771 461 84 169 0");
        assertThat(summaryResults).contains("Start time 605.0 Route = 0 236 507 331 751 102 57 237 742 201 730 101 289 785 483 639 643 801 578 476 167 780 734 364 799 0");
        assertThat(summaryResults).contains("Start time 610.03 Route = 0 89 345 607 270 554 707 694 447 651 608 374 768 408 743 184 700 451 263 357 215 126 172 11 336 381 787 116 761 0");
        assertThat(summaryResults).contains("Start time 625.0 Route = 0 685 243 187 589 104 220 403 308 649 338 560 61 580 119 448 280 285 77 782 488 0");
        assertThat(summaryResults).contains("Start time 720.72 Route = 0 720 537 540 558 363 21 324 599 519 157 329 273 0");
        assertThat(summaryResults).contains("Start time 733.0 Route = 0 227 590 306 223 583 706 0");
        assertThat(summaryResults).contains("Start time 769.0 Route = 0 416 563 544 252 705 718 545 479 482 653 474 194 74 22 0");
        assertThat(summaryResults).contains("Start time 1281.0 Route = 0 12 117 659 48 230 359 0");
        assertThat(summaryResults).contains("Start time 1645.0 Route = 0 206 18 0");
        assertThat(summaryResults).contains("Start time 1687.0 Route = 0 406 68 687 679 0");
        assertThat(summaryResults).contains("Start time 1908.0 Route = 0 290 514 171 750 423 266 0");
        assertThat(summaryResults).contains("Start time 2082.0 Route = 0 570 278 0");
        assertThat(summaryResults).contains("Start time 2297.0 Route = 0 348 696 0");
        assertThat(summaryResults).contains("Start time 2614.0 Route = 0 567 388 0");
        assertThat(summaryResults).contains("Start time 3633.0 Route = 0 731 239 0");
        assertThat(summaryResults).contains("Start time 4663.0 Route = 0 745 82 0");
        assertThat(summaryResults).contains("Start time 4920.0 Route = 0 199 193 0");
    }
}
