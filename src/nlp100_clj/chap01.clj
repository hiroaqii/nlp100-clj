(ns nlp100-clj.chap01
  "第1章: 準備運動"
  (:require [clojure.string :as str]
            [clojure.set :as set]))


(defn p00 
  "00. 文字列の逆順

  文字列 stressed の文字を逆に（末尾から先頭に向かって）並べた文字列を得よ．
  （reverse関数を使えば即解決） "
  [s]
  (apply str (reduce #(cons %2 %1) [] s)))


(defn p01
  "01. 「パタトクカシーー」

  「パタトクカシーー」という文字列の1,3,5,7文字目を取り出して連結した文字列を得よ．"
  [s]
  (apply str (take-nth 2 s)))


(defn p02
  "02. 「パトカー」＋「タクシー」＝「パタトクカシーー」

  「パトカー」＋「タクシー」の文字を先頭から交互に連結して文字列「パタトクカシーー」を得よ．
  (interleave関数を使えば即解決）"
  [s1 s2]
  (apply str (mapcat #(list %1 %2) s1 s2)))


(defn p03
  "03. 円周率

  Now I need a drink, alcoholic of course, after the heavy lectures involving quantum mechanics.
  という文を単語に分解し，各単語の（アルファベットの）文字数を先頭から出現順に並べたリストを作成せよ．"
  [s]
  (map #(count %) (re-seq #"\w+" s)))


(defn p04
  "04. 元素記号

  Hi He Lied Because Boron Could Not Oxidize Fluorine. New Nations Might Also Sign Peace Security Clause. Arthur King Can.
  という文を単語に分解し，1, 5, 6, 7, 8, 9, 15, 16, 19番目の単語は先頭の1文字，それ以外の単語は先頭に2文字を取り出し，
  取り出した文字列から単語の位置（先頭から何番目の単語か）への連想配列（辞書型もしくはマップ型）を作成せよ．"
  [words]
  (let [idxs #{1 5 6 7 8 9 15 16 19}
        cols (map-indexed (fn [idx word] [(inc idx) word]) (re-seq #"\w+" words))
        cols (map (fn [[idx word]] [(if (contains? idxs idx) (str (first word)) (apply str (take 2 word))) idx]) cols)]
    (into (hash-map) cols)))


(defn p05
  "05. n-gram

  与えられたシーケンス（文字列やリストなど）からn-gramを作る関数を作成せよ．この関数を用い，
  I am an NLPer という文から単語bi-gram，文字bi-gramを得よ．"
  ([s] (p05 s 2))
  ([s n] (map #(apply str %) (partition n 1 s))))


(defn p06
  "06. 集合

  paraparaparadiseとparagraphに含まれる文字bi-gramの集合を，それぞれ, XとYとして求め，
  XとYの和集合，積集合，差集合を求めよ．さらに，'se'というbi-gramがXおよびYに含まれるかどうかを調べよ"
  [s1 s2]
  (let [x (into #{} (p05 s1))
        y (into #{} (p05 s2))]
    {:union (set/union x y)
     :difference (set/difference x y)
     :intersection (set/intersection x y)}))

(defn p07
  "07. テンプレートによる文生成

   引数x, y, zを受け取り「x時のyはz」という文字列を返す関数を実装せよ．さらに，x=12, y=気温, z=22.4として，実行結果を確認せよ"
  [x y z]
  (format "%s時の%sは%s" x y z))


(defn p08
  "08. 暗号文

  与えられた文字列の各文字を，以下の仕様で変換する関数cipherを実装せよ．
  ・英小文字ならば(219 - 文字コード)の文字に置換
  ・その他の文字はそのまま出力
  この関数を用い，英語のメッセージを暗号化・復号化せよ．"
  [s]
  (apply str (map
              #(if (Character/isLowerCase %)
                 (->> (int %)
                      (- 219)
                      (char))
                 %)
              s)))



(defn p09-rand [s]
  "先頭と末尾はそのまま、それ以外はランダムに並び替える"
  (loop [ret [(first s)]
         col (drop-last (rest s))]
    (let [n (rand-int (count col))]
      (if (empty? col)
        (conj ret (last s))
        (recur
         (conj ret (nth col n))
         (concat (take n col) (drop (inc n) col)))))))


(defn p09
  "09. Typoglycemia

  スペースで区切られた単語列に対して，各単語の先頭と末尾の文字は残し，
  それ以外の文字の順序をランダムに並び替えるプログラムを作成せよ．
  ただし，長さが４以下の単語は並び替えないこととする．適当な英語の文
  （例えばI couldn't believe that I could actually understand what I was reading : the phenomenal power of the human mind .）
  を与え，その実行結果を確認せよ．"
  [s]
  (->> (str/split s #" ")
       (map #(if (>= 4 (count %)) % (apply str (p09-rand %))))
       (str/join " ")))
