#! /usr/bin/ruby

# 順列生成
class Array
  def perms
    return [[]] if empty?
    uniq.inject([]) do |rs, h|
      tmp = self.dup
      tmp.delete_at(index(h))
      rs + tmp.perms.map {|t| [h] + t }
    end
  end
  def total
    self.inject(0){|t,a| t += a}
  end
end

# a : [[1,1,1], [1,1,1], [1,1,1], [1,1,1], [2]]
def ptn(c,a)
  if a.size == 1 then
    return [a]
  end
  ret = Array.new
  # 重ならないパターン
  ret += a.perms
  # 重なるパターン
  h1 = Hash.new
  for i in 0..a.size-1
    for j in i+1..a.size-1
      key = [a[i], 0, a[j]].to_s
      if !h1.key?(key) then
        h1.store(key, nil)
        h2 = Hash.new
        # a[i]とa[j]を範囲をずらしながら重ねる
        for k in 0..a[i].size+a[j].size
          t = [0]*a[j].size + a[i] + [0]*a[j].size
          for m in 0..a[j].size-1
            t[k+m] += a[j][m]
          end
          # 余分な0を取り除く
          t.delete(0)
          # 4より大きい値がないかチェック
          next if t.any? {|v| v > 4}
          # 9より長くないかチェック
          next if t.size >9
          # 重複チェック
          if !h2.key?(t.to_s) then
            h2.store(t.to_s, nil)
            # 残り
            t2 = a.dup
            t2.delete_at(i)
            t2.delete_at(j-1)
            # 再帰呼び出し
            r = ptn(c+1,[t]+t2)
            #if c==0
             #   printf("%d\n%s\n--\n",c,r)
            #end
            ret += r
          end
        end
      end
    end
  end
  return ret
end

# printf("%s",ptn(0,[[3],[3],[3],[3],[2]]))
printf("%s",ptn(0,[[2],[2],[2],[2],[2],[2],[2]]))
# a.perms 只会排列最外层元素,并且会保证不会存在equals相同的组合
# ret = Array.new
# a = [1,1,2]
# a = [a,0,[1,1,2]]
# ret += [0,1,2]
# c = [0]*3+[1,2]+[0]*2
# printf("%s\n",a.perms)
# printf("%s\n",a)
# printf("%s\n",ret)
# printf("%s\n",c)