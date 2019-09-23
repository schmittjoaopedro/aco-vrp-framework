package com.github.schmittjoaopedro.thesis;

import com.github.schmittjoaopedro.vrp.thesis.RealSolver;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class RealScenariosTest {

    private static final String eletroNacionalScenario;

    private static final String transligueDay16Scenario;

    static {
        eletroNacionalScenario = Paths.get(RealScenariosTest.class.getClassLoader().getResource("real/eletronacional").getFile().substring(1)).toString();
        transligueDay16Scenario = Paths.get(RealScenariosTest.class.getClassLoader().getResource("real/transligue/day16").getFile().substring(1)).toString();
    }

    @Test
    public void eletroNacionalVanScenarioTest() {
        File duration = Paths.get(eletroNacionalScenario, "duration.json").toFile();
        File distances = Paths.get(eletroNacionalScenario, "distance.json").toFile();
        File geoCodes = Paths.get(eletroNacionalScenario, "geocode.json").toFile();
        RealSolver realSolver = new RealSolver();
        realSolver.setDistanceFile(distances);
        realSolver.setGeoCodeFile(geoCodes);
        realSolver.setDurationFile(duration);
        realSolver.setBoundWorkingDayTimeByMaxTimeWindows(false);
        realSolver.setNumVehicles(1);
        realSolver.setVehiclesCapacity(1000.0);
        realSolver.setWorkingDayTime(24 * 60 * 60);
        realSolver.addRealRequest(0, 2, 1.0);
        realSolver.addRealRequest(0, 2, 1.0);
        realSolver.addRealRequest(0, 3, 1.0);
        realSolver.addRealRequest(0, 3, 1.0);
        realSolver.addRealRequest(0, 3, 1.0);
        realSolver.addRealRequest(0, 4, 1.0);
        realSolver.addRealRequest(0, 4, 1.0);
        realSolver.addRealRequest(0, 4, 1.0);
        realSolver.addRealRequest(0, 4, 1.0);
        realSolver.addRealRequest(0, 4, 1.0);
        realSolver.addRealRequest(0, 4, 1.0);
        realSolver.addRealRequest(0, 4, 1.0);
        realSolver.addRealRequest(0, 4, 1.0);
        realSolver.addRealRequest(0, 5, 1.0);
        realSolver.addRealRequest(0, 6, 1.0);
        realSolver.addRealRequest(0, 6, 1.0);
        realSolver.addRealRequest(0, 7, 1.0);
        realSolver.addRealRequest(0, 8, 1.0);
        realSolver.addRealRequest(0, 5, 1.0);
        realSolver.addRealRequest(0, 9, 1.0);

        realSolver.optimize();
        String result = realSolver.getTechnicalSolution();
        System.out.println(result);
        assertThat(result).contains("Optimized durations 1.14 (h)\n" +
                "Optimized distances 46.01 (km)\n" +
                "\n" +
                "Vehicle 0, Time = 1.14 (hs), Distance 46.01 (km), Route = [\n" +
                "\t0 -> R. Benjamin Constant, 2628 - Costa e Silva, Joinville - SC, 89217-260, Brazil\n" +
                "\t2 -> R. Olaria, 208 - Floresta, Joinville - SC, 89211-360, Brazil\n" +
                "\t9 -> Rod. do Arroz, 1330 - Vila Nova, Joinville - SC, 89237-338, Brazil\n" +
                "\t5 -> R. Anaburgo, 3558 - Zona Industrial Norte, Joinville - SC, Brazil\n" +
                "\t3 -> Complexo Helmuth Miers - Zona Industrial Norte - Zona Industrial Norte, Joinville - SC, 89219-512, Brazil\n" +
                "\t7 -> Av. Fábio Perini - Centro (Pirabeiraba), Joinville - SC, Brazil\n" +
                "\t4 -> SC-418, 6901 - Zona Industrial Norte, Joinville - SC, 89219-600, Brazil\n" +
                "\t6 -> R. Rui Barbosa, 169 - Zona Industrial Norte, Joinville - SC, 89219-520, Brazil\n" +
                "\t8 -> R. Benjamin Constant, 3228 - Glória, Joinville - SC, 89217-301, Brazil\n" +
                "\t0 -> R. Benjamin Constant, 2628 - Costa e Silva, Joinville - SC, 89217-260, Brazil\n" +
                "]\n" +
                "Requests: [5, 8, 9, 10, 2, 3, 4, 0, 1, 14, 15, 6, 13, 12, 7, 16, 11, 18, 17, 19]");
    }

    @Test
    public void eletroNacionalMotoScenarioTest() {
        File duration = Paths.get(eletroNacionalScenario, "duration.json").toFile();
        File distances = Paths.get(eletroNacionalScenario, "distance.json").toFile();
        File geoCodes = Paths.get(eletroNacionalScenario, "geocode.json").toFile();
        RealSolver realSolver = new RealSolver();
        realSolver.setDistanceFile(distances);
        realSolver.setGeoCodeFile(geoCodes);
        realSolver.setDurationFile(duration);
        realSolver.setBoundWorkingDayTimeByMaxTimeWindows(false);
        realSolver.setNumVehicles(2);
        realSolver.setVehiclesCapacity(100.0);
        realSolver.setWorkingDayTime(24 * 60 * 60);
        realSolver.addRealRequest(0, 10, 15.0);
        realSolver.addRealRequest(0, 11, 15.0);
        realSolver.addRealRequest(0, 12, 15.0);
        realSolver.addRealRequest(1, 0, 15.0);
        realSolver.addRealRequest(0, 6, 15.0);
        realSolver.addRealRequest(0, 13, 15.0);
        realSolver.addRealRequest(0, 14, 15.0);
        realSolver.addRealRequest(0, 15, 15.0);
        realSolver.addRealRequest(0, 16, 15.0);
        realSolver.addRealRequest(0, 16, 15.0);
        realSolver.addRealRequest(0, 17, 15.0);

        realSolver.optimize();
        String result = realSolver.getTechnicalSolution();
        System.out.println(result);
        assertThat(result).contains("Optimized durations 1.79 (h)\n" +
                "Optimized distances 79.98 (km)\n" +
                "\n" +
                "Vehicle 0, Time = 1.79 (hs), Distance 79.98 (km), Route = [\n" +
                "\t0 -> R. Benjamin Constant, 2628 - Costa e Silva, Joinville - SC, 89217-260, Brazil\n" +
                "\t13 -> BR-101, 3248 - Santa Catarina, Joinville - SC, 89233-190, Brazil\n" +
                "\t16 -> R. Hans Dieter Schmidt, 1500 - Zona Industrial Norte, Joinville - SC, 89219-504, Brazil\n" +
                "\t6 -> R. Rui Barbosa, 169 - Zona Industrial Norte, Joinville - SC, 89219-520, Brazil\n" +
                "\t0 -> R. Benjamin Constant, 2628 - Costa e Silva, Joinville - SC, 89217-260, Brazil\n" +
                "\t11 -> R. Dona Francisca, 12346 - Pirabeiraba, Joinville - SC, 89239-250, Brazil\n" +
                "\t15 -> Av. Santos Dumont, 3939 - Santo Antônio, Joinville - SC, 89223-600, Brazil\n" +
                "\t17 -> R. Jacutinga, 206 - Iririú, Joinville - SC, 89227-365, Brazil\n" +
                "\t12 -> R. Albano Schmidt, 3400 - Comasa, Joinville - SC, 89205-100, Brazil\n" +
                "\t14 -> R. Santa Cruz, 28 - Boa Vista, Joinville - SC, 89206-810, Brazil\n" +
                "\t1 -> R. Pref. Helmuth Fallgatter, 2260 - Boa Vista, Joinville - SC, 89206-101, Brazil\n" +
                "\t10 -> R. Frederico Hubner, 118 - América, Joinville - SC, 89204-280, Brazil\n" +
                "\t0 -> R. Benjamin Constant, 2628 - Costa e Silva, Joinville - SC, 89217-260, Brazil\n" +
                "]\n" +
                "Requests: [7, 6, 1, 5, 9, 3, 0, 8, 4, 10, 2]");
    }

    @Test
    public void transligueDay16ScenarioTest() {
        File duration = Paths.get(transligueDay16Scenario, "duration.json").toFile();
        File distances = Paths.get(transligueDay16Scenario, "distance.json").toFile();
        File geoCodes = Paths.get(transligueDay16Scenario, "geocode.json").toFile();
        RealSolver realSolver = new RealSolver();
        realSolver.setDistanceFile(distances);
        realSolver.setGeoCodeFile(geoCodes);
        realSolver.setDurationFile(duration);
        realSolver.setBoundWorkingDayTimeByMaxTimeWindows(false);
        realSolver.setNumVehicles(50);
        realSolver.setVehiclesCapacity(10000.0);
        realSolver.setWorkingDayTime(8.8 * 60.0 * 60.0);
        realSolver.setServiceTime(10.0 * 60.0);
        realSolver.addRealRequest(0, 1, 0.34);
        realSolver.addRealRequest(0, 2, 0.08);
        realSolver.addRealRequest(0, 3, 1.95);
        realSolver.addRealRequest(0, 4, 1);
        realSolver.addRealRequest(0, 5, 2);
        realSolver.addRealRequest(0, 6, 4.55);
        realSolver.addRealRequest(0, 7, 3.5);
        realSolver.addRealRequest(0, 8, 2.28);
        realSolver.addRealRequest(0, 9, 0.13);
        realSolver.addRealRequest(0, 10, 1.17);
        realSolver.addRealRequest(0, 11, 1);
        realSolver.addRealRequest(0, 12, 1.67);
        realSolver.addRealRequest(0, 13, 15.6);
        realSolver.addRealRequest(0, 14, 0.3);
        realSolver.addRealRequest(0, 15, 35.5);
        realSolver.addRealRequest(0, 16, 4.44);
        realSolver.addRealRequest(0, 17, 1.16);
        realSolver.addRealRequest(0, 18, 4.06);
        realSolver.addRealRequest(0, 19, 7.15);
        realSolver.addRealRequest(0, 20, 4.2);
        realSolver.addRealRequest(0, 21, 2.1);
        realSolver.addRealRequest(0, 22, 4.55);
        realSolver.addRealRequest(0, 23, 0.59);
        realSolver.addRealRequest(0, 24, 3.96);
        realSolver.addRealRequest(0, 25, 3.81);
        realSolver.addRealRequest(0, 26, 2.1);
        realSolver.addRealRequest(0, 27, 0.71);
        realSolver.addRealRequest(0, 28, 1);
        realSolver.addRealRequest(0, 29, 21);
        realSolver.addRealRequest(0, 30, 2.1);
        realSolver.addRealRequest(0, 31, 4.55);
        realSolver.addRealRequest(0, 32, 2);
        realSolver.addRealRequest(0, 33, 43);
        realSolver.addRealRequest(0, 34, 25);
        realSolver.addRealRequest(0, 35, 138);
        realSolver.addRealRequest(0, 36, 7);
        realSolver.addRealRequest(0, 37, 31.8);
        realSolver.addRealRequest(0, 38, 3);
        realSolver.addRealRequest(0, 39, 19.7);
        realSolver.addRealRequest(0, 40, 1);

        realSolver.optimize();
        String result = realSolver.getTechnicalSolution();
        System.out.println(result);
        assertThat(result).contains("Optimized durations 11.55 (h)\n" +
                "Optimized distances 697.18 (km)\n" +
                "\n" +
                "Vehicle 0, Time = 3.49 (hs), Distance 189.99 (km), Route = [\n" +
                "\t0 -> Ícaro Express - Centro (Pirabeiraba), Joinville - SC, Brasil\n" +
                "\t36 -> R. Euzebio de Queiroz, 499 - Glória, Joinville - SC, 89203-100, Brasil\n" +
                "\t40 -> BR-101, 10431 - Santa Catarina, Joinville - SC, 89233-190, Brasil\n" +
                "\t15 -> Rua C, 199 - Gravatá, Penha - SC, 88385-000, Brasil\n" +
                "\t28 -> Av. Paraná, 308 - Centro, Barra Velha - SC, 88390-000, Brasil\n" +
                "\t39 -> R. Corá Coralina, 49 - Boehmerwald, Joinville - SC, 89235-440, Brasil\n" +
                "\t38 -> R. Sérgio Luís Soares, 202 - Boehmerwald, Joinville - SC, 89235-540, Brasil\n" +
                "\t29 -> R. Albano Schmidt, 2220 - Comasa, Joinville - SC, 89227-701, Brasil\n" +
                "\t31 -> R. Ex-Combatentes, 125 - Saguaçu, Joinville - SC, 89221-100, Brasil\n" +
                "\t37 -> Av. José Vieira, 2025 - América, Joinville - SC, 89204-110, Brasil\n" +
                "\t30 -> R. Itá, 397 - Bom Retiro, Joinville - SC, 89223-140, Brasil\n" +
                "\t35 -> R. Dona Francisca, 7200 - Zona Industrial Norte, Joinville - SC, Brasil\n" +
                "\t34 -> R. Dona Francisca, 8300 - Distrito Industrial, Joinville - SC, 89219-600, Brasil\n" +
                "\t0 -> Ícaro Express - Centro (Pirabeiraba), Joinville - SC, Brasil\n" +
                "]\n" +
                "Requests: [33, 31, 35, 29, 28, 34, 36, 30, 37, 38, 14, 39, 27]\n" +
                "\n" +
                "Vehicle 1, Time = 3.59 (hs), Distance 223.75 (km), Route = [\n" +
                "\t0 -> Ícaro Express - Centro (Pirabeiraba), Joinville - SC, Brasil\n" +
                "\t23 -> R. Jaci Ramos, 308 - São Vicente, Itajaí - SC, 88309-270, Brasil\n" +
                "\t5 -> R. Pedro Joaquim Viêira, 174 - São Judas, Itajaí - SC, 88303-460, Brasil\n" +
                "\t12 -> R. Pedro Joaquim Viêira, 173 - São Judas, Itajaí - SC, 88303-460, Brasil\n" +
                "\t6 -> R. Santo Antônio, 200 - São Judas, Itajaí - SC, 88303-310, Brasil\n" +
                "\t2 -> R. Duque de Caxias, 79 - Vila Operaria, Itajaí - SC, 88303-230, Brasil\n" +
                "\t7 -> Avenida Getúlio Vargas, 524 - Vila Operaria, Itajaí - SC, 88303-220, Brasil\n" +
                "\t24 -> R. Alberto Werner, 1163 - Centro, Itajaí - SC, 88302-001, Brasil\n" +
                "\t21 -> Rua Acyr Cunha, 108 - Dom Bosco, Itajaí - SC, 88307-030, Brasil\n" +
                "\t3 -> R. Lico Amaral, 215 - Dom Bosco, Itajaí - SC, 88307-100, Brasil\n" +
                "\t25 -> R. Nivaldo Detoie, 143 - Ressacada, Itajaí - SC, 88307-326, Brasil\n" +
                "\t26 -> R. Jorge Mattos, 53 - Centro, Itajaí - SC, 88302-130, Brasil\n" +
                "\t19 -> R. Evaristo da Veiga, 5 - Praia dos Amores, Balneário Camboriú - SC, 88331-435, Brasil\n" +
                "\t18 -> Av. Brasil, 692 - Centro, Balneário Camboriú - SC, 88330-762, Brasil\n" +
                "\t1 -> R. Jamaica, 415 - Nações, Balneário Camboriú - SC, 88338-250, Brasil\n" +
                "\t13 -> Rod. Gov. Mário Covas, 233 - Espinheiros, Itajaí - SC, 88316-300, Brasil\n" +
                "\t0 -> Ícaro Express - Centro (Pirabeiraba), Joinville - SC, Brasil\n" +
                "]\n" +
                "Requests: [11, 17, 6, 1, 12, 24, 5, 4, 23, 0, 25, 20, 22, 18, 2]\n" +
                "\n" +
                "Vehicle 2, Time = 4.46 (hs), Distance 283.44 (km), Route = [\n" +
                "\t0 -> Ícaro Express - Centro (Pirabeiraba), Joinville - SC, Brasil\n" +
                "\t33 -> R. Agostinho José Cognaco, 40 - Costa e Silva, Joinville - SC, 89220-370, Brasil\n" +
                "\t8 -> R. Benjamin Dagnone, 3817 - Rio Do Meio, Itajaí - SC, 88316-100, Brasil\n" +
                "\t10 -> R. Santo Agostinho, 62 - Bairro Rio Pequeno, Camboriú - SC, 88343-232, Brasil\n" +
                "\t4 -> Porto Belo - SC, 88210-000, Brasil\n" +
                "\t14 -> 2ª Avenida, 528 - Andorinha, Itapema - SC, 88220-000, Brasil\n" +
                "\t16 -> R. 246, 246 - Meia Praia, Itapema - SC, 88220-000, Brasil\n" +
                "\t11 -> Rua Victório Fornerolli, 285 - Estaleirinho, Balneário Camboriú - SC, 88334-610, Brasil\n" +
                "\t27 -> R. 3110, 215 - Centro, Balneário Camboriú - SC, 88330-287, Brasil\n" +
                "\t20 -> R. 2600, 67 - Centro, Balneário Camboriú - SC, 88330-385, Brasil\n" +
                "\t17 -> Av. Atlântica, 23 - Centro, Balneário Camboriú - SC, 88330-003, Brasil\n" +
                "\t22 -> R. 101, 144 - Centro, Balneário Camboriú - SC, 88330-687, Brasil\n" +
                "\t9 -> Rua 904, 720 - Centro, Balneário Camboriú - SC, 88330-590, Brasil\n" +
                "\t0 -> Ícaro Express - Centro (Pirabeiraba), Joinville - SC, Brasil\n" +
                "]\n" +
                "Requests: [19, 13, 26, 21, 32, 8, 16, 15, 3, 10, 9, 7]");
    }

}
