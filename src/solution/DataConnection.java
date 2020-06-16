package solution;

import java.io.IOException;

interface DataConnection {
    int loadData(int sum) throws IOException;;

    void saveData(int year, int qq) throws IOException;
}