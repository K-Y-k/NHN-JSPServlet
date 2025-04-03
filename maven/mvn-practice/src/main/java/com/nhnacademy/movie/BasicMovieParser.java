package com.nhnacademy.movie;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicMovieParser implements MovieParser {

    @Override
    public List<Movie> parse() throws IOException {
        // 영화 데이터를 담을 리스트 선언
        List<Movie> movieList = new ArrayList<Movie>();
        
        // movies.csv파일 가져옴
        InputStream movieFileAsStream = getMovieFileAsStream();

        try (BufferedReader movieFileReader = new BufferedReader(new InputStreamReader(movieFileAsStream));) {
            // 첫줄은 현재 필요 없으므로 읽음 처리
            String line = movieFileReader.readLine();

            // 이후 라인들의 데이터를 담기 위한 읽기
            while ((line = movieFileReader.readLine()) != null) {
                // ,를 기준으로 분리
                String[] line_split = line.split(",");

                // |를 기준으로 분리한 장르들 Set에 적용
                Set<String> genreSet = new HashSet<>();
                String[] genre_split = line_split[2].split("\\|");
                for (String genre : genre_split) {
                    genreSet.add(genre);
                }

                // 위 필드로 구성한 영화 객체 생성 및 리스트에 삽입
                Movie movie = new Movie(Long.parseLong(line_split[0]), line_split[1], genreSet);
                movieList.add(movie);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return movieList;
    }
}