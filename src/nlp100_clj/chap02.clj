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


(defn p12
  "12. 1列目をcol1.txtに，2列目をcol2.txtに保存

  各行の1列目だけを抜き出したものをcol1.txtに，
  2列目だけを抜き出したものをcol2.txtとしてファイルに保存せよ．
  確認にはcutコマンドを用いよ．"
  ([n] (p12 file-path n))
  ([file n]
   (let [col (->> (slurp file)
                  (str/split-lines)
                  (map #(str/split % #"\t"))
                  (map #(str (nth % (dec n)) "\n")))]
     (spit (format "out/col%d.txt" n) (str/join col)))))


(defn p13
  "13. col1.txtとcol2.txtをマージ

  12で作ったcol1.txtとcol2.txtを結合し，
  元のファイルの1列目と2列目をタブ区切りで並べたテキストファイルを作成せよ．
  確認にはpasteコマンドを用いよ．"
  ([] (p13 "out/col1.txt" "out/col2.txt" "out/p13_out.tsv"))
  ([in1 in2 out]
   (let [col1 (str/split-lines (slurp in1))
         col2 (str/split-lines (slurp in2))]
     (->> (map #(str %1 "\t" %2 "\n") col1 col2)
          (str/join)
          (spit out)))))


(defn p14
  "14. 先頭からN行を出力

  自然数Nをコマンドライン引数などの手段で受け取り，
  入力のうち先頭のN行だけを表示せよ．
  確認にはheadコマンドを用いよ．"
  ([] (p14 file-path 10))
  ([n] (p14 file-path n))
  ([file n]
   (let [col (take n (str/split-lines (slurp file)))
         s (str/join (map #(str % "\n") col))]
     (print s))))


(defn p15
  "15. 末尾のN行を出力

  自然数Nをコマンドライン引数などの手段で受け取り，
  入力のうち末尾のN行だけを表示せよ．
  確認にはtailコマンドを用いよ．"
  ([] (p15 file-path 10))
  ([n] (p15 file-path n))
  ([file n]
   (let [col (take-last n (str/split-lines (slurp file)))
         s (str/join (map #(str % "\n") col))]
     (print s))))


(defn p16
  "16. ファイルをN分割する

  自然数Nをコマンドライン引数などの手段で受け取り，
  入力のファイルを行単位でN分割せよ．
  同様の処理をsplitコマンドで実現せよ．"
  ([n] (p16 n file-path "p16-"))
  ([n file prefix]
   (let [col (->> (slurp file)
                  (str/split-lines)
                  (map #(str % "\n"))
                  (partition-all n)
                  (map #(str/join %)))]
     (map-indexed #(spit (str "out/" prefix % ".txt") %2) col))))


(defn p17
  "17. １列目の文字列の異なり

  1列目の文字列の種類（異なる文字列の集合）を求めよ．
  確認にはsort, uniqコマンドを用いよ．"
  ([] (p17 file-path))
  ([file]
   (let [col (->> (slurp file)
                  (str/split-lines)
                  (map #(str/split % #"\t"))
                  (map first))]
     (sort (into #{} col)))))
