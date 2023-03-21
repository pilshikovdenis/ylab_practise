package homework3.file_sort;

import java.io.*;
import java.util.*;

public class Sorter {
    private final int blockSize = 15_000_000;
    private final String resultFilePath = "final.txt";
    private final int bufferSize = 100_000;
    public File sortFile(File dataFile) throws IOException {

        System.out.println("Начало нарезки на блоки: " + new Date());
        // разрезаем входной файл на части
        cropFile(dataFile,blockSize);
        System.out.println("Конец нарезки на блоки: " + new Date());

        HashMap<String, ArrayDeque<Long>> buffersMap = new HashMap<>();
        HashMap<String, Scanner> inputStreamsMap = new HashMap<>();

        System.out.println("Начало сортировки: " + new Date());
        // Создаем потоки ввода для всех файлов в директории
        initializeInputStreamsAndBuffers(inputStreamsMap, buffersMap);

        // загружаем данные с кажого потока в буфер
        for (String key : buffersMap.keySet()) {
            loadDataToBuffer(buffersMap, inputStreamsMap, key, bufferSize);
        }


        try (PrintWriter pw = new PrintWriter(resultFilePath)){
            while (buffersMap.size() != 0) {
                String key = findMinValueInBuffer(buffersMap);

                // удаляем значение из буфера и записываем в выходной файл
                Long value = buffersMap.get(key).removeFirst();
                pw.println(value);

                // если буфер откуда взяли значение пуст, загружаем данные
                if (buffersMap.get(key).size() == 0) {
                    loadDataToBuffer(buffersMap, inputStreamsMap, key, bufferSize);
                }

            }
            pw.flush();
            System.out.println("Конец сортировки: " + new Date());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new File(resultFilePath);
    }

    // выводим содержимое кажого буфера
    private static void printAllBuffers(HashMap<String, ArrayDeque<Long>> buffersMap ) {
        for (ArrayDeque<Long> e: buffersMap.values()) {
            System.out.println("B: " + e);
        }
        System.out.println();

    }

    // Метод загружает по stringsCount строк из файла file, сортирует их и пишет в отдельный файл
    // Все файлы сохраняются в output/, каждый с отдельным префиксом
    private static void cropFile(File file, int stringsCount) {
        // если каталога для вывовода файлов не существует, создаем его
        new File("output").mkdir();

        try (Scanner inputScanner = new Scanner(new FileInputStream(file))){
            int outputFileIndex = 1;
            ArrayList<Long> buffer = new ArrayList<>();
            while (inputScanner.hasNextLong()) {
                buffer.add(inputScanner.nextLong());
                if (buffer.size() == stringsCount || !inputScanner.hasNextLong()) {
                    Collections.sort(buffer);
                    writeArrayToFile("output/out" + outputFileIndex + ".txt", buffer);
                    outputFileIndex++;
                    buffer.clear();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void writeArrayToFile(String filePath, ArrayList<Long> buffer) throws FileNotFoundException {
        try(PrintWriter pw = new PrintWriter(filePath)) {
            for (long e : buffer) {
                pw.println(e);
            }
            pw.flush();
        }

    }

    // Метод ищет все файлы в директории output/ с префиксом out
    // Для каждого файла создается создается буфер, открывается поток ввода и сохраняется ссылка на них
    public static void initializeInputStreamsAndBuffers(HashMap<String, Scanner> inputStreamsMap,
                                                        HashMap<String, ArrayDeque<Long>> buffersMap) {

        int fileIndex = 1;
        String path = "output/out" + fileIndex + ".txt";
        File f = new File(path);
        while ((f = new File(path)).exists()) {
            try {
                // Открываем поток ввода для файла
                inputStreamsMap.put(path, new Scanner(new FileInputStream(f)));
                // Инициализируем буфер для этого потока
                buffersMap.put(path, new ArrayDeque<>());
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            fileIndex++;
            path = "output/out" + fileIndex + ".txt";
        }
    }

    // Метод  находит буфер по ключу key, в коллекции buffersMap и заполняет его если есть чем.
    // В противном случае удаляет его
    public static void loadDataToBuffer(HashMap<String, ArrayDeque<Long>> buffersMap, HashMap<String,
            Scanner> inputStreamsMap, String bufferKey, int bufferSize) {
        Scanner inputScanner = inputStreamsMap.get(bufferKey);
        // Если загружать больше нечего, закрываем поток ввода и удаляем его, удаляем буфер для этого файла
        if (!inputScanner.hasNextLong()) {
            buffersMap.remove(bufferKey);
            inputScanner.close();
            inputStreamsMap.remove(bufferKey);

        } else {
            // Если есть что грузить - грузим пока буфер не заполнится
            ArrayDeque<Long> buffer = buffersMap.get(bufferKey);
            while (inputScanner.hasNext() && buffer.size() <= bufferSize) {
                buffer.addLast(inputScanner.nextLong());
            }
        }
    }

    // поиск минимального значения в буферах (по первым элементам)
    // возвращает ключ буфера с минимальным элементом
    public static String findMinValueInBuffer(HashMap<String, ArrayDeque<Long>> map) {

        // Сохраняем ключ самого первого буфера
        String key = null;
        for (String s : map.keySet()) {
            key = s;
            break;
        }
        // если в других буферах есть более низкое значение, сохраняем ключ
        for (Map.Entry<String, ArrayDeque<Long>> element : map.entrySet()) {
            if (element.getValue().getFirst() < map.get(key).getFirst()) {
                key = element.getKey();
            }
        }
        return key;
    }
}
