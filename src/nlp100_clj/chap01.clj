(ns nlp100-clj.chap01
  (:require [clojure.string :as str]))


(defn p00 
  "文字列 stressed の文字を逆に（末尾から先頭に向かって）並べた文字列を得よ．
  （reverse関数を使えば即解決） "
  []
  (apply str (reduce #(cons %2 %1) [] "stressed")))


(defn p01
  "「パタトクカシーー」という文字列の1,3,5,7文字目を取り出して連結した文字列を得よ．"
  []
  (apply str (take-nth 2 "パタトクカシーー")))


(defn p02
  "「パトカー」＋「タクシー」の文字を先頭から交互に連結して文字列「パタトクカシーー」を得よ．
  (interleave関数を使えば即解決）"
  []
  (apply str (mapcat #(list %1 %2) "パトカー" "タクシー")))


(defn p03
  "Now I need a drink, alcoholic of course, after the heavy lectures involving quantum mechanics.
  という文を単語に分解し，各単語の（アルファベットの）文字数を先頭から出現順に並べたリストを作成せよ．"
  []
  (let [s "Now I need a drink, alcoholic of course, after the heavy lectures involving quantum mechanics."]
    (map #(count (re-seq #"[a-zA-Z]" %)) (str/split s #" "))))


(defn p04
  "Hi He Lied Because Boron Could Not Oxidize Fluorine. New Nations Might Also Sign Peace Security Clause. Arthur King Can.
  という文を単語に分解し，1, 5, 6, 7, 8, 9, 15, 16, 19番目の単語は先頭の1文字，それ以外の単語は先頭に2文字を取り出し，
  取り出した文字列から単語の位置（先頭から何番目の単語か）への連想配列（辞書型もしくはマップ型）を作成せよ．"
  []
  (let [words "Hi He Lied Because Boron Could Not Oxidize Fluorine. New Nations Might Also Sign Peace Security Clause. Arthur King Can."
        idxs #{1 5 6 7 8 9 15 16 19}
        cols (map-indexed (fn [idx word] [(inc idx) word]) (str/split words #" "))
        cols (map (fn [[idx word]] [(if (contains? idxs idx) (str (first word)) (apply str (take 2 word))) idx]) cols)]
    (into (hash-map) cols)))





























(interleave [1 2 3] [4 5 6])



(first (partition-by #(=  %) ))q

(take-nth 3 (range 10))


















