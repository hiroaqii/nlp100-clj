(ns nlp100-clj.chap02
  "第3章: 正規表現"
  (:require [clojure.string :as str]
            [cheshire.core :as json]))

(def file-path "resources/jawiki-country.json")

(defn p20
  "Wikipedia記事のJSONファイルを読み込み，「イギリス」に関する記事本文を表示せよ．
  問題21-29では，ここで抽出した記事本文に対して実行せよ．"
  []
  (let [lines (str/split-lines (slurp file-path))
        jsons (map #(json/parse-string %) lines)
        m (first (filter #(= (% "title") "イギリス") jsons))]
    (m "text")))






































(json/generate-string {:foo "bar" :baz 5})

(def a (json/parse-stream (io/reader file-path)))

(def a (str/split-lines (slurp file-path)))




(last b)




