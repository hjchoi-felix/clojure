(ns slot.machine
  (:gen-class))

(comment
  슬롯 머신 계획
  로컬용 머신을 클로저로 작성
  현재 적용된 기능 중 핵심 내용 위주로
  간결하게 정리된 머신을 작성

  함수형으로 작성하려면 명령 위주로 모아야 하나?
  객체 설계에 익숙해서 어떤 구성으로 코드를 만들어야 할지 난감하네

  객체 설계라면 머신 클래스 만들고 시작할 텐데...
  함수형에서는 뭘로 시작하나?
  막막한데?

  객체 기반에서 행위 기반으로???
    객체 기반에서는 행위 주체가 먼저 있고 행위 주체간 통신을 메소드로 구현한다.
    함수 기반에서는 행위들을 먼저 찾는가?
      슬롯머신의 행위
        spin
        주된 행위는 이게 다인듯
  )

(def symbols {
               :w1 0
               :s1 1
               :s2 2
               :s3 3
               :s4 4
               :s5 5
               :a 6
               :k 7
               :q 8
               :j 9
               :bs 10
                })

(def paylines [[1 1 1 1 1]
                       [0 0 0 0 0]
                       [2 2 2 2 2]])

(def reelstrip [[0 1 2 3 4 5 6 7 8 9 10]
                      [0 1 2 3 4 5 6 7 8 9 10]
                      [0 1 2 3 4 5 6 7 8 9 10]
                      [0 1 2 3 4 5 6 7 8 9 10]
                      [0 1 2 3 4 5 6 7 8 9 10]])

(defn test1 [stop]
;  (map #(.indexOf #1) stop))
   (map (fn [e] ((reelstrip (e 0)) (e 1))) (map-indexed vector stop)))

(defn make-symbolset [stop]
     [(get (get reelstrip 0) (get stop 0))
      (get (get reelstrip 1) (get stop 1))
      (get (get reelstrip 2) (get stop 2))
      (get (get reelstrip 3) (get stop 3))
      (get (get reelstrip 4) (get stop 4))])

(defn spin []
  (let [stop (vec (map rand-int(
                         map count reelstrip)))
         vs [[1 2 3 4 5]]]
    {:stop stop
;     :vs [[0 0 0 0 0] [0 0 0 0 0] [0 0 0 0 0]]
      :vs (test1 stop)
     :reward 9999}))
