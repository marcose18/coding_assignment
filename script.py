import requests
import sys
from bs4 import BeautifulSoup


def print_ratings_of_movie(url, movie_name):
    response = requests.get(url)
    soup = BeautifulSoup(response.text, "html.parser")
    movie_list = soup.find_all('div', class_ = 'ratingValue')
    if(len(movie_list) == 0):
        print("Ratings for the movie", movie_name, "is not available at IMDB, Needs atleast 5 ratings")
    else :
        print("Ratings of the movie", movie_name, "is:", movie_list[0].strong.span.text)
        #comment below line if printing total votes is not desired 
        print(movie_list[0].strong['title'])

if __name__ == '__main__' :
    base_url = 'https://www.imdb.com'
    input = '+'.join(sys.argv[1].split(' '))
    url = 'https://www.imdb.com/find?ref_=nv_sr_fn&q=' + input + '&s=all'
    response = requests.get(url)
    soup = BeautifulSoup(response.text, "html.parser")
    movie_list = soup.find_all('tr', class_ = 'findResult odd')
    movie_list += soup.find_all('tr', class_ = 'findResult even')
    for movie in movie_list:
        if(movie.a['href'].startswith('/title')):
            soup = BeautifulSoup(str(movie),"html.parser")
            movie_name = soup.find_all('td')[1]
            print_ratings_of_movie(base_url + movie.a['href'], movie_name.text)