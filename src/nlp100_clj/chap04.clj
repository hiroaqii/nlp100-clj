(ns nlp100-clj.chap04
  "第4章: 形態素解析

  メモ：MeCabではなくkuromojiを使用。
  "
  (:require [clojure.string :as str]
            [incanter.charts :as charts]
            [incanter.core :as incanter])
  (:import [com.atilika.kuromoji.ipadic Token Tokenizer]))


(def tokenizer (Tokenizer.))


(defn tokenize [s]
  (let [col (.tokenize tokenizer s)]
    (map #(apply str (.getSurface %1) "," (.getAllFeatures %1)) col)))


(defn init
  "夏目漱石の小説『吾輩は猫である』の文章（neko.txt）をkuromojiを使って形態素解析し，
  その結果をneko.txt.kuromojiというファイルに保存
  (オリジナルの問題はMeCabを使用しているが、Kuromojiを使用する）"

  []
  (let [tokens (flatten (map #(tokenize %) (str/split-lines (slurp "resources/neko.txt"))))
        s (str/join "\n" tokens)]
    (spit "resources/neko.txt.kuromoji" s)))


(defn p30
  "30. 形態素解析結果の読み込み

  形態素解析結果（neko.txt.kuromoji）を読み込むプログラムを実装せよ．
  ただし，各形態素は表層形（surface），基本形（base），品詞（pos），品詞細分類1（pos1）をキーとするマッピング型に格納し，
  1文を形態素（マッピング型）のリストとして表現せよ．第4章の残りの問題では，ここで作ったプログラムを活用せよ．
  "
  []
  (let [lines (->> (slurp "resources/neko.txt.kuromoji")
                   (str/split-lines )
                   (map #(str/split % #",")))]
    (map
     #(hash-map :surface (nth % 0)
                :pos     (nth % 1)
                :pos1    (nth % 2)
                :base    (nth % 7)
                )
         lines)))


(defn p31
  "31. 動詞

  動詞の表層形をすべて抽出せよ．
  "
  []
  (->> (filter #(= "動詞" (:pos %)) (p30))
       (map #(:surface %))))


(defn p32
  "32. 動詞の原形

  動詞の原形をすべて抽出せよ．"
  []
  (->> (filter #(= "動詞" (:pos %))(p30))
       (map #(:base %))))


(defn p33
  "33. サ変名詞

  サ変接続の名詞をすべて抽出せよ．"
  []
  (->> (filter #(and (= "名詞" (:pos %)) (= "サ変接続" (:pos1 %)))(p30))
       (map #(:surface %))))


(defn p34
  "34. 「AのB」

  2つの名詞が「の」で連結されている名詞句を抽出せよ．"
  []
  (->> (partition 3 1 (p30))
       (filter #(and
                 (= "名詞" (:pos (first %)))
                 (= "の"   (:surface (second %)))
                 (= "名詞" (:pos (last %)))))
       (map #(str (:surface (first %))
                  (:surface (second %))
                  (:surface (last %))))))


(defn p35
  "35. 名詞の連接

  名詞の連接（連続して出現する名詞）を最長一致で抽出せよ．"
  []
  (->> (partition-by #(:pos %) (p30))
       (filter #(= "名詞" (:pos (first %))))
       (map #(map :surface %))
       (map #(apply str %))))


(defn p36
  "36. 単語の出現頻度

  文章中に出現する単語とその出現頻度を求め，出現頻度の高い順に並べよ"

  []
  (->> (map :surface (p30))
       (frequencies)
       (sort-by val >)))


(defn p37
  "37. 頻度上位10語

  出現頻度が高い10語とその出現頻度をグラフ（例えば棒グラフなど）で表示せよ．"
  []
  (let [top10 (take 10 (p36))]
    (incanter/view (charts/bar-chart
                    (map first top10)  ;単語
                    (map second top10) ;出現回数
                    :x-label "word"
                    :y-label "frequency"))))


(defn p38
  "38. ヒストグラム

  単語の出現頻度のヒストグラム（横軸に出現頻度，縦軸に出現頻度をとる単語の種類数を棒グラフで表したもの）を描け．"
  []
  (let [ret (->> (map :surface (p30))
                 (frequencies)       ;単語と単語の出現頻度         ex. [["の" 9193] ["。" 7486] ....]
                 (map second)        ;単語の出現頻度              ex. [9193 7486 ....]
                 (frequencies)       ;単語の出現頻度と単語の種類数  ex. [[9193 1] [7486 1] ....]
                 (sort-by second >)) ];単語の種類数でソート         ex. [[6132 1] [2262 2] ...]
    (incanter/view (charts/bar-chart
                    (map first ret)
                    (map second ret)
                    :x-label "word count"
                    :y-label "frequency"))))


(defn p39
  "39. Zipfの法則

  単語の出現頻度順位を横軸，その出現頻度を縦軸として，両対数グラフをプロットせよ． "

  []
  (let [ret (->> (map :surface (p30))
                 (frequencies)
                 (map second)
                 (frequencies)
                 (sort-by second >))
        chart (charts/scatter-plot
               (map first ret)
               (map second ret))]
    (-> chart
        (charts/set-axis :y (charts/log-axis :label "word count"))
        (charts/set-axis :x (charts/log-axis :label "frequency"))
        (incanter/view))))
