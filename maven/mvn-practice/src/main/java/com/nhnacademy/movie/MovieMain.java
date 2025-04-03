package com.nhnacademy.movie;

import java.util.List;

public class MovieMain {

    public static void main(String[] args) {
        // BasicMovieParser로 적용한 케이스
        MovieParser basicMovieParser = new BasicMovieParser();

        try {
            List<Movie> movieList = basicMovieParser.parse();
            for (Movie movie : movieList) {
                System.out.println(movie);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


        // ApacheCommonsCsvMovieParser로 적용한 케이스
        MovieParser csvMovieParser = new ApacheCommonsCsvMovieParser();

        try {
            List<Movie> movieList = csvMovieParser.parse();
            for (Movie movie : movieList) {
                System.out.println(movie);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
