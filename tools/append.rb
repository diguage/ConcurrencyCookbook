
# 扫描指定目录下到所有文件
def scanFiles(dir) 
  list = []
  Dir.foreach(dir) do |f|
    if f.to_s =~ /\w*md$/ 
      list << f
    end
  end
  return list.sort
end

scanFiles(Dir.pwd + "/../docs/").each do |f|

  file = File.open(Dir.pwd + "/../docs/#{f.to_s}", "a")
  
  template = File.new("Template.md")
  template.each do |line|
    file.puts line
  end
  file.close
end
