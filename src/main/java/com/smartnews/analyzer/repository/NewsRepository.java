package com.smartnews.analyzer.repository;

import com.smartnews.analyzer.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    
    // 최신 뉴스 조회 (기본 10개)
    List<News> findTop10ByOrderByPublishedDateDesc();
    
    // 카테고리별 뉴스 조회
    List<News> findByCategoryOrderByPublishedDateDesc(String category);
    
    // 제목으로 검색 (대소문자 구분 없음)
    List<News> findByTitleContainingIgnoreCase(String keyword);
    
    // 내용으로 검색
    List<News> findByContentContainingIgnoreCase(String keyword);
    
    // 제목 또는 내용으로 검색
    @Query("SELECT n FROM News n WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(n.content) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "ORDER BY n.publishedDate DESC")
    List<News> findByTitleOrContentContaining(@Param("keyword") String keyword);
    
    // 특정 날짜 이후의 뉴스 조회
    List<News> findByPublishedDateAfterOrderByPublishedDateDesc(LocalDateTime date);
    
    // 요약이 있는 뉴스만 조회 (AI 분석 완료된 뉴스)
    @Query("SELECT n FROM News n WHERE n.summary IS NOT NULL AND n.summary != '' ORDER BY n.publishedDate DESC")
    List<News> findNewsWithSummary();
    
    // 요약이 없는 뉴스 조회 (AI 분석이 필요한 뉴스)
    @Query("SELECT n FROM News n WHERE n.summary IS NULL OR n.summary = '' ORDER BY n.publishedDate DESC")
    List<News> findNewsWithoutSummary();
    
    // 인기 뉴스 조회 (조회수 기준)
    List<News> findTop10ByOrderByViewCountDesc();
    
    // 카테고리별 뉴스 개수 조회
    @Query("SELECT n.category, COUNT(n) FROM News n GROUP BY n.category")
    List<Object[]> countNewsByCategory();
    
    // 출처별 뉴스 조회
    List<News> findBySourceOrderByPublishedDateDesc(String source);
    
    // 특정 기간의 뉴스 조회
    @Query("SELECT n FROM News n WHERE n.publishedDate BETWEEN :startDate AND :endDate ORDER BY n.publishedDate DESC")
    List<News> findNewsBetweenDates(@Param("startDate") LocalDateTime startDate, 
                                   @Param("endDate") LocalDateTime endDate);
    
    // 최신 뉴스 N개 조회 (개수 지정 가능)
    @Query("SELECT n FROM News n ORDER BY n.publishedDate DESC LIMIT :limit")
    List<News> findLatestNews(@Param("limit") int limit);
    
    // 중복 URL 확인 (같은 뉴스 중복 저장 방지)
    boolean existsByUrl(String url);
    
    // 제목으로 중복 확인
    boolean existsByTitle(String title);
}