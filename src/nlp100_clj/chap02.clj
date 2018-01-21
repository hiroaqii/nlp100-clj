(ns nlp100-clj.chap02
  "第2章: UNIXコマンドの基礎"
  (:require [clojure.string :as str]))

(def file-path "resources/hightemp.txt")

(defn p10
  "10. 行数のカウント

  行数をカウントせよ．確認にはwcコマンドを用いよ．"
  ([] (p10 file-path))
  ([file](count (str/split  (slurp file) #"\n"))))


(defn p11
  "11. タブをスペースに置換

  タブ1文字につきスペース1文字に置換せよ．
  確認にはsedコマンド，trコマンド，もしくはexpandコマンドを用いよ．"
  ([] (p11 file-path))
  ([file](str/replace (slurp file) "\t" " ")))


