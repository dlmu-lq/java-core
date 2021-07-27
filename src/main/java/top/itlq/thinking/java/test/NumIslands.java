package top.itlq.thinking.java.test;

class NumIslands {
    public int numIslands(char[][] grid) {
        if(grid.length == 0){
            return 0;
        }
        int re = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for(int i=0,j=0;i<grid.length&&j<grid[0].length;){
            if(!visited[i][j]){
                if(grid[i][j] == '1'){
                    searchRightBottom(i,j,grid,visited);
                    re++;
                }else{
                    visited[i][j] = true;
                }
            }
            if(i < grid.length){
                i++;
            }else{
                i = 0;
                j++;
            }
        }
        return re;
    }

    private void searchRightBottom(int i, int j, char[][] grid, boolean[][] visited){
        if(i<grid.length&&j<grid[0].length){
            if(visited[i][j]){
                return;
            }
            visited[i][j] = true;
            if(grid[i][j] == '1'){
                searchRightBottom(i+1, j, grid, visited);
                searchRightBottom(i, j+1, grid, visited);
            }
        }
    }

    public static void main(String[] args) {
        char[][] test = {{'1','1','0','0','0'},{'1','1','0','0','0'},{'0','0','1','0','0'},{'0','0','0','1','1'}};
        new NumIslands().numIslands(test);
    }
}