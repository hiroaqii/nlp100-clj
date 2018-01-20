(ns nlp100-clj.chap01-test
  (:require [nlp100-clj.chap01 :refer :all]
            [clojure.test :refer :all]))


(deftest p00-test
  (is (= (p00 "stressed") "desserts")))

(deftest p01-test
  (is (= (p01 "パタトクカシー") "パトカー")))

(deftest p02-test
  (is (= (p02 "パトカー" "タクシー") "パタトクカシーー")))

(deftest p03-test
  (is (= (p03 "Now I need a drink, alcoholic of course, after the heavy lectures involving quantum mechanics.")
         [3 1 4 1 5 9 2 6 5 3 5 8 9 7 9])))

(deftest p04-test
  (is (= (p04 "Hi He Lied Because Boron Could Not Oxidize Fluorine. New Nations Might Also Sign Peace Security Clause. Arthur King Can.")
         {"H" 1, "He" 2, "Li" 3, "Be" 4, "B" 5, "C" 6, "N" 7, "O" 8, "F" 9, "Ne" 10, "Na" 11, "Mi" 12, "Al" 13, "Si" 14, "P" 15, "S" 16, "Cl" 17 ,"Ar" 18, "K" 19, "Ca" 20})))
