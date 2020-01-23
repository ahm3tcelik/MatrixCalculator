package com.ahmetc.matriscalculator;
import android.widget.EditText;
import java.util.Arrays;

public class Matris {
    public static int checkInvo(EditText[][] matris, int x, int y) {
        if(x != y) return 0;
        if(Arrays.deepEquals(matrisCarp(matris, matris, x,y, x,y), getBirimMatris(x))) {
            return 1;
        }
        else return 2;
    }

    public static String[][] matrisCarp(EditText[][] matris1, EditText[][] matris2, int satir1, int sutun1,
                                        int satir2, int sutun2) {
        String[][] newMatris = new String[satir1][sutun2];
        if(satir2 != sutun1) return  newMatris;
        for(int i = 0; i < satir1; i++) {
            for(int j = 0; j < sutun2; j++) {
                int toplam = 0;
                for(int k = 0; k < satir2; k++) {
                    if(matris1[i][k].getText().toString().trim().isEmpty()) {
                        matris1[i][k].setText("0");
                    }
                    if(matris2[k][j].getText().toString().trim().isEmpty()) {
                        matris2[k][j].setText("0");
                    }
                    toplam += (Integer.valueOf(matris1[i][k].getText().toString().trim()) *
                            Integer.valueOf(matris2[k][j].getText().toString().trim()));
                    newMatris[i][j] = String.valueOf(toplam);
                }
            }
        }
        return newMatris;
    }

    public static String[][] getTers(EditText[][] matris, int satir, int sutun) {
        String[][] newMatris = new String[satir][sutun];
        String[][] oldMatris = Matris.getNumberFromMatris(matris,satir,sutun);
        int determinant = Integer.valueOf(Matris.getDeterminant(Matris.getNumberFromMatris(matris,satir,sutun)));
        if(determinant == 0) return newMatris;
        for(int i = 0; i < satir; i++){
            for(int j = 0; j < sutun; j++) {
                newMatris[i][j] = ((Integer.valueOf(oldMatris[(i + 1) % 3][(j + 1) % 3]) * Integer.valueOf(oldMatris[(i + 2) % 3][(j + 2) % 3])) - (Integer.valueOf(oldMatris[(i + 1) % 3][(j + 2) % 3]) * Integer.valueOf(oldMatris[(i + 2) % 3][(j + 1) % 3]))) + "/" + determinant;
                System.out.print(newMatris[i][j]);
            }
            System.out.print("\n");
        }
        return newMatris;
    }

    public static String[][] matrisTopla(EditText[][] matris1, EditText[][] matris2, int satir, int sutun) {
        String[][] newMatris = new String[satir][sutun];
        for(int i = 0; i < satir; i++) {
            for(int j = 0; j < sutun; j++) {
                if(matris1[i][j].getText().toString().trim().isEmpty())
                    matris1[i][j].setText("0");
                if(matris2[i][j].getText().toString().trim().isEmpty())
                    matris2[i][j].setText("0");
                newMatris[i][j] = String.valueOf(Integer.valueOf(matris1[i][j].getText().toString().trim()) +
                        Integer.valueOf(matris2[i][j].getText().toString().trim()));
            }
        }
        return newMatris;
    }

    public static String[][] matrisCikar(EditText[][] matris1, EditText[][] matris2, int satir, int sutun) {
        String[][] newMatris = new String[satir][sutun];
        for(int i = 0; i < satir; i++) {
            for(int j = 0; j < sutun; j++) {
                if(matris1[i][j].getText().toString().trim().isEmpty())
                    matris1[i][j].setText("0");
                if(matris2[i][j].getText().toString().trim().isEmpty())
                    matris2[i][j].setText("0");
                newMatris[i][j] = String.valueOf(Integer.valueOf(matris1[i][j].getText().toString().trim()) -
                        Integer.valueOf(matris2[i][j].getText().toString().trim()));
            }
        }
        return newMatris;
    }

    public static String[][] getNumberFromMatris(EditText[][] matris, int satir, int sutun) {
        String[][] result = new String[satir][sutun];
        for(int i = 0; i < satir; i++) {
            for (int j = 0; j < sutun; j++) {
                if(matris[i][j].getText().toString().trim().isEmpty())
                    result[i][j] = "0";
                else result[i][j] = matris[i][j].getText().toString().trim();
            }
        }
        return result;
    }
    public static String[][] getBirimMatris(int m) {
        String[][] result = new String[m][m];
        int sayac = 0;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < m; j++) {
                result[i][j] = "0";
            }
            result[i][sayac++] = "1";
        }
        return result;
    }

    public static String getDeterminant(String[][] matris) {
        String[][] matrisD = new String[5][3];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 3; j++) {
                if(i == 3 || i == 4) {
                    matrisD[i][j] = matris[i-3][j];
                } else matrisD[i][j] = matris[i][j];
            }
        }
        int sonucA =  0, sonucB = 0, j = 0;
        for(int i = 0; i < 3; i++) {
            sonucA +=  Integer.valueOf(matrisD[i][j]) * Integer.valueOf(matrisD[i+1][j+1]) * Integer.valueOf(matrisD[i+2][j+2]);
        }
        j = 2;
        for(int i = 0; i < 3; i++) {
            sonucB += Integer.valueOf(matrisD[i][j]) * Integer.valueOf(matrisD[i+1][j-1]) * Integer.valueOf(matrisD[i+2][j-2]);
        }
        return String.valueOf(sonucA - sonucB);
    }
}
